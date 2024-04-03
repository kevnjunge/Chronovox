package com.example.chronovox.presentation.screens.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.chronovox.R
import com.example.chronovox.navigation.Screen
import com.example.chronovox.presentation.components.PaperText
import com.example.chronovox.theme.MicadoYellow
import com.example.chronovox.theme.paperBackGround
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    journalEntryId: String,
    navController: NavController
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()

    val isJournalEntryIdBlank = journalEntryId.isNotBlank()
    val icon = if (isJournalEntryIdBlank) Icons.Default.Update
         else Icons.Default.Check

    LaunchedEffect(key1 = Unit){
        if (isJournalEntryIdBlank){
            detailViewModel?.getJournalEntry(journalEntryId)
        }else{
            detailViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.app_bar_logo),
                            contentDescription = "App Logo",
                        )
                    }
                },
                colors = topAppBarColors(
                    containerColor = paperBackGround
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack(route = Screen.Home.route, inclusive = true)
                            navController.navigate(Screen.Home.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            tint = MicadoYellow,
                            contentDescription = "Cancel"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                        if (isJournalEntryIdBlank){
                            detailViewModel?.updateJournalEntry(journalEntryId)
                        }else{
                            detailViewModel?.addJournalEntry()
                        }
                }
                    ) {

                Icon(
                    imageVector = icon,
                    tint = MicadoYellow,
                    contentDescription = null )
            }
                }

            )
        }
    ) {paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ){
            if (detailUiState.journalEntryAddedStatus){
                scope.launch {
                    snackBarHostState.showSnackbar("Journal Added Successfully")
                    detailViewModel?.resetJournalEntryAddedStatus()
                    navController.popBackStack(route = Screen.Home.route, inclusive = true)
                    navController.navigate(Screen.Home.route)
                }
            }

            if (detailUiState.updatedJournalEntryStatus){
                scope.launch {
                    snackBarHostState.showSnackbar("Journal Updated Successfully")
                    detailViewModel?.resetJournalEntryAddedStatus()
                    navController.popBackStack(route = Screen.Home.route, inclusive = true)
                    navController.navigate(Screen.Home.route)
                }
            }
            
            PaperText(detailViewModel = detailViewModel)

        }

    }

}