package com.example.chronovox.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chronovox.presentation.screens.signIn.SignInScreen
import com.example.chronovox.presentation.screens.signUp.SignUpScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {
        composable(
            route = Screen.SignIn.route
        ){
            SignInScreen(navController)
        }
        composable(
            route = Screen.SignUp.route
        ){
            SignUpScreen(navController)
        }

    }
}