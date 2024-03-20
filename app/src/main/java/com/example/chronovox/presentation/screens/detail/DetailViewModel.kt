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

    fun onJournalEntryChange (journalEntry: String){
        detailUiState = detailUiState.copy(journalEntry = journalEntry)
    }

    fun addJournalEntry(){
        if (hasUser){
            repository.addJournalEntry(
                userId = user!!.uid,
                journalEntry = detailUiState.journalEntry,
                timestamp = Timestamp.now()
            ){
                detailUiState = detailUiState.copy(journalEntryAddedStatus = it)
            }
        }
    }

    fun setEditFields(journalEntry: Journals){
        detailUiState = detailUiState.copy(
            journalEntry = journalEntry.journalEntry
        )
    }

    fun getJournalEntry(journalEntryId:String){
        repository.getJournalEntry(
            journalEntryId = journalEntryId,
            onError = {},
        ){
            detailUiState = detailUiState.copy(selectedJournalEntry = it)
            detailUiState.selectedJournalEntry?.let{it1 -> setEditFields(it1)}
        }
    }

    fun updateJournalEntry(
        journalEntryId: String
    ){
        repository.updateJournalEntry(
            journalEntryId = journalEntryId,
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
    val journalEntry:String = "",
    val journalEntryAddedStatus: Boolean = false,
    val updatedJournalEntryStatus: Boolean = false,
    val selectedJournalEntry: Journals? = null
)