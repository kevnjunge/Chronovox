package com.example.chronovox

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.chronovox.navigation.SetUpNavGraph
import com.example.chronovox.presentation.screens.signIn.SignInViewModel

@Composable
fun ChronovoxApp(signInViewModel: SignInViewModel){
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize()
    ){

        SetUpNavGraph(navController = navController, signInViewModel = signInViewModel)

    }
}