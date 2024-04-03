package com.example.chronovox.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chronovox.model.Journals
import com.example.chronovox.repository.Resources
import com.example.chronovox.repository.StorageRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel() {
    var homeUiState by mutableStateOf(HomeUiState())

    val hasUser: Boolean
        get() = repository.hasUser()
    private val userId: String
        get() = repository.getUserId()

    fun loadJournalEntries(){
        if (hasUser){
            if (userId.isNotBlank()){
                getUserJournalEntries(userId)
            }
        }else{
            homeUiState = homeUiState.copy(journalEntryList = Resources.Error(
                throwable = Throwable(message = "User Not Logged In")
            ))
        }
    }

    private fun getUserJournalEntries(userId:String) = viewModelScope.launch {
        repository.getUserJournals(userId).collect{
            homeUiState = homeUiState.copy(journalEntryList = it)
        }
    }

    fun deleteJournalEntry(journalEntryId:String) = repository.deleteJournalEntry(journalEntryId){
        homeUiState = homeUiState.copy(journalDeletedStatus = it)
    }

    fun resetJournalDeletedStatus() {
        homeUiState = homeUiState.copy(journalDeletedStatus = false)
    }

    fun signOut() = repository.signOut()


}

data class HomeUiState(
    val journalEntryList:Resources<List<Journals>> = Resources.Loading(),
    val journalDeletedStatus: Boolean = false
)


