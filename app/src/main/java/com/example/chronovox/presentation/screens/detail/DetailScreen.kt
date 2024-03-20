package com.example.chronovox.presentation.screens.detail

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.chronovox.navigation.Screen
import com.example.chronovox.presentation.components.PaperText
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    journalEntryId: String,
    navController: NavController
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()

    val isFormsNotBlank = detailUiState.journalEntry.isNotBlank()

    val isJournalEntryIdBlank = journalEntryId.isNotBlank()
    val icon = if (isJournalEntryIdBlank) Icons.Default.Refresh
         else Icons.Default.Check

    LaunchedEffect(key1 = Unit){
        if (isJournalEntryIdBlank){
            detailViewModel?.getJournalEntry(journalEntryId)
        }else{
            detailViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
        AnimatedVisibility(visible = isFormsNotBlank) {
            FloatingActionButton(onClick = {
                if (isJournalEntryIdBlank){
                    detailViewModel?.updateJournalEntry(journalEntryId)
                }else{
                    detailViewModel?.addJournalEntry()
                }
            }) {

                Icon(imageVector = icon, contentDescription = null )
            }
        }
        }
    ) {paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ){
            if (detailUiState.journalEntryAddedStatus){
                scope.launch {
                    snackbarHostState.showSnackbar("Journal Added Successfully")
                    detailViewModel?.resetJournalEntryAddedStatus()
                    navController.navigate(route = Screen.Home.route)
                }
            }

            if (detailUiState.updatedJournalEntryStatus){
                scope.launch {
                    snackbarHostState.showSnackbar("Journal Updated Successfully")
                    detailViewModel?.resetJournalEntryAddedStatus()
                    navController.navigate(route = Screen.Home.route)
                }
            }
            
            PaperText(detailViewModel = detailViewModel)

        }

    }

}