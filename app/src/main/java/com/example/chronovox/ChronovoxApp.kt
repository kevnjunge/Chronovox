package com.example.chronovox

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.chronovox.navigation.SetUpNavGraph
import com.example.chronovox.presentation.screens.auth.signIn.SignInViewModel
import com.example.chronovox.presentation.screens.detail.DetailViewModel
import com.example.chronovox.presentation.screens.home.HomeViewModel

@Composable
fun ChronovoxApp(
    signInViewModel: SignInViewModel,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel
) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        SetUpNavGraph(navController = navController, signInViewModel = signInViewModel,homeViewModel, detailViewModel)

    }
}