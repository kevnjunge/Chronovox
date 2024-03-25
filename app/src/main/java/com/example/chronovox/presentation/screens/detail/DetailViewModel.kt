package com.example.chronovox.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chronovox.model.Journals
import com.example.chronovox.repository.StorageRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser

class DetailViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {
  var detailUiState by mutableStateOf(DetailUiState())

    private val hasUser:Boolean
        get() = repository.hasUser()

    private val user:FirebaseUser?
        get() = repository.user()

    fun onJournalEntryChange(title: String? = null, content: String? = null) {
        val updatedTitle = title ?: detailUiState.journalEntryTitle
        val updatedContent = content ?: detailUiState.journalEntry
        // Update the state with the new values for title and content
        detailUiState = detailUiState.copy(
            journalEntryTitle = updatedTitle,
            journalEntry = updatedContent
        )
    }



    fun addJournalEntry(){
        if (hasUser){
            repository.addJournalEntry(
                userId = user!!.uid,
                journalEntryTitle = detailUiState.journalEntryTitle,
                journalEntry = detailUiState.journalEntry,
                timestamp = Timestamp.now()
            ){
                detailUiState = detailUiState.copy(journalEntryAddedStatus = it)
            }
        }
    }

    private fun setEditFields(journalEntryTitle: String, journalEntry: String){
        detailUiState = detailUiState.copy(
            journalEntryTitle = journalEntryTitle,
            journalEntry = journalEntry
        )
    }

    fun getJournalEntry(journalEntryId:String){
        repository.getJournalEntry(
            journalEntryId = journalEntryId,
            onError = {},
        ){journalEntry ->
            journalEntry?.let {
                detailUiState = detailUiState.copy(selectedJournalEntry = it)
                setEditFields(it.journalEntryTitle, it.journalEntry)
            }
        }
    }

    fun updateJournalEntry(
        journalEntryId: String
    ){
        repository.updateJournalEntry(
            journalEntryId = journalEntryId,
            journalEntryTitle = detailUiState.journalEntryTitle,
            journalEntry = detailUiState.journalEntry
        ){
            detailUiState = detailUiState.copy(updatedJournalEntryStatus = it)
        }
    }

    fun resetJournalEntryAddedStatus(){
        detailUiState = detailUiState.copy(
            journalEntryAddedStatus = false,
            updatedJournalEntryStatus = false)
    }

    fun resetState(){
        detailUiState = DetailUiState()
    }



}

data class  DetailUiState(
    val journalEntryTitle:String = "",
    val journalEntry:String = "",
    val journalEntryAddedStatus: Boolean = false,
    val updatedJournalEntryStatus: Boolean = false,
    val selectedJournalEntry: Journals? = null
)