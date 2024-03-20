package com.example.chronovox.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.chronovox.presentation.screens.detail.DetailScreen
import com.example.chronovox.presentation.screens.detail.DetailViewModel
import com.example.chronovox.presentation.screens.home.HomeScreen
import com.example.chronovox.presentation.screens.home.HomeViewModel
import com.example.chronovox.presentation.screens.signIn.SignInScreen
import com.example.chronovox.presentation.screens.signIn.SignInViewModel
import com.example.chronovox.presentation.screens.signUp.SignUpScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    signInViewModel: SignInViewModel,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel
) {

    // Check session status before composing
    val hasActiveSession =
        signInViewModel.checkActiveSession() // This method should check for active session

    NavHost(
        navController = navController,
        startDestination = if (hasActiveSession) Screen.Home.route else Screen.SignIn.route
    ) {
        composable(
            route = Screen.SignIn.route
        ) {
            SignInScreen(navController)
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(navController)
        }
        composable(
            route = Screen.Home.route
        ) {
            // Check again inside composable for extra safety
            if (hasActiveSession) {
                HomeScreen(
                    navController,
                    homeViewModel = homeViewModel,
                    onJournalEntryClick = { journalEntryId ->
                        navController.navigate(
                            Screen.Detail.route + "?id=$journalEntryId"
                        ){
                            launchSingleTop = true
                        }
                    })
            }
        }
        composable(
            route = Screen.Detail.route +"?id={id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
                defaultValue = ""
            })
        ) { entry->
            DetailScreen(
                navController = navController,
                detailViewModel = detailViewModel,
                journalEntryId = entry.arguments?.getString("id") as String
            )
        }
    }
}
