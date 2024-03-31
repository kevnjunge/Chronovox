package com.example.chronovox.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chronovox.R
import com.example.chronovox.model.Journals
import com.example.chronovox.navigation.Screen
import com.example.chronovox.repository.Resources
import com.example.chronovox.theme.BgWhite
import com.example.chronovox.theme.CardBG
import com.example.chronovox.theme.MicadoYellow
import com.example.chronovox.theme.paperBackGround
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel?,
    onJournalEntryClick: (id: String) -> Unit
) {

    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()
    var openDialog by remember {
        mutableStateOf(false)
    }

    var selectedJournalEntry: Journals? by remember {
        mutableStateOf(null)
    }

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = Unit) {
        homeViewModel?.loadJournalEntries()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(route = Screen.Detail.route)

                },
                shape = CircleShape,
                containerColor = MicadoYellow
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = BgWhite
                )
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = paperBackGround
                ),
                actions = {
                    IconButton(onClick = {
                        homeViewModel?.signOut()
                        navController.navigate(Screen.SignIn.route)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            tint = MicadoYellow,
                            contentDescription = null
                        )

                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(R.drawable.app_bar_logo),
                            contentDescription = "App Logo",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(paperBackGround),
        ) {

            when (homeUiState.journalEntryList) {

                is Resources.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }

                is Resources.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(
                            homeUiState.journalEntryList.data ?: emptyList()
                        ) { journalEntryItem ->

                            JournalEntryItem(
                                journalEntry = journalEntryItem,
                                onLongClick = {
                                    openDialog = true
                                    selectedJournalEntry = journalEntryItem
                                },
                            ) {
                                onJournalEntryClick.invoke(journalEntryItem.documentId)

                            }

                        }
                    }

                }

                else -> {
                    Text(
                        text = homeUiState
                            .journalEntryList.throwable?.localizedMessage ?: "Unknown Error",
                        color = Color.Red
                    )
                }
            }


        }

    }

    LaunchedEffect(key1 = homeViewModel?.hasUser) {
        if (homeViewModel?.hasUser == false) {
            navController.navigate(Screen.SignIn.route)
        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JournalEntryItem(
    journalEntry: Journals,
    onLongClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .combinedClickable(
                onLongClick = { onLongClick.invoke() },
                onClick = { onClick.invoke() }
            )
            .padding(8.dp)
            .fillMaxWidth(),
        colors = cardColors(CardBG)

    ) {
        Text(
            text = journalEntry.journalEntryTitle,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier.padding(4.dp)
        )

        Spacer(modifier = Modifier.size(4.dp))

        CompositionLocalProvider() {
            Text(
                text = journalEntry.journalEntry,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(4.dp),
                maxLines = 4
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        CompositionLocalProvider() {
            Text(
                text = formatDate(journalEntry.timestamp),
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.End),
                maxLines = 4
            )
        }
    }

}

private fun formatDate(timestamp: Timestamp): String {
    val simpleDateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return simpleDateFormat.format(timestamp.toDate())
}
