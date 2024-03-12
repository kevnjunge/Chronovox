package com.example.chronovox.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.chronovox.navigation.Screen
import com.example.chronovox.presentation.components.PaperText
import com.example.chronovox.presentation.components.RegularButtonComponent
import com.example.chronovox.presentation.components.RegularTextComponent
import com.example.chronovox.presentation.screens.signUp.SignUpViewModel
import com.example.chronovox.theme.BgWhite
import com.example.chronovox.theme.ChronoWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = ChronoWhite,
                    titleContentColor = Color.Black
                ),
                title = { Text(text = "Chronovox") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(ChronoWhite),
        ) {
//            RegularButtonComponent(value = "LogOut", onButtonClicked = {
//                signUpViewModel.logout()
//                signUpViewModel.navigateToSignIn = {
//                    navController.navigate(route = Screen.SignIn.route)
//                }
//            }, isEnabled = true)

            PaperText()


        }

    }


}