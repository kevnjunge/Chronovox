package com.example.chronovox.repository

import com.example.chronovox.model.Journals
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val JOURNAL_COLLECTION_REF = "journals"

class StorageRepository() {

    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val journalRef: CollectionReference =
        Firebase.firestore.collection(JOURNAL_COLLECTION_REF)

    fun getUserJournals(
        userId: String
    ): Flow<Resources<List<Journals>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = journalRef
                .orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val journals = snapshot.toObjects(Journals::class.java)
                        Resources.Success(data = journals)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)


                }

        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }

        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getJournalEntry(
        journalEntryId:String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Journals?) -> Unit
    ){
        journalRef.document(journalEntryId).get().addOnSuccessListener {
            onSuccess.invoke(it?.toObject(Journals::class.java))
        }
            .addOnFailureListener {result ->
                onError.invoke(result.cause)

            }
    }

    fun addJournalEntry(
        userId: String,
        journalEntry:String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit
    ){
        val documentId = journalRef.document().id
        val journalEntry = Journals(userId,journalEntry,timestamp, documentId = documentId)

        journalRef.document(documentId).set(journalEntry).addOnCompleteListener { result ->
            onComplete.invoke(result.isSuccessful)
        }
    }

    fun deleteJournalEntry(journalEntryId:String,  onComplete: (Boolean) -> Unit){
        journalRef.document(journalEntryId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }

    }

    fun updateJournalEntry(
        journalEntryId: String,
        journalEntry: String,
        onResult:(Boolean) -> Unit
    ){
        //Use hashmap when you have multiple entries
        val updateData = hashMapOf<String,Any>(
            "entry" to journalEntry,

        )
        journalRef.document(journalEntryId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }
    }

    fun signOut() = Firebase.auth.signOut()


}


sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)
}