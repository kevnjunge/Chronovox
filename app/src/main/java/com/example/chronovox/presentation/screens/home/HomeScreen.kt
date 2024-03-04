package com.example.chronovox.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.chronovox.navigation.Screen
import com.example.chronovox.presentation.components.RegularButtonComponent
import com.example.chronovox.presentation.components.RegularTextComponent
import com.example.chronovox.presentation.screens.signUp.SignUpViewModel
import com.example.chronovox.theme.ChronoWhite

@Composable
fun HomeScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = viewModel()
) {
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(ChronoWhite)
            .padding(28.dp)
    ){
        Column (
            modifier = Modifier.fillMaxSize()
        ){
            RegularTextComponent(value = "Home")
            RegularButtonComponent(value = "LogOut", onButtonClicked = {
                signUpViewModel.logout()
                signUpViewModel.navigateToSignIn = {
                    navController.navigate(route = Screen.SignIn.route)
                }
            }, isEnabled = true)
            
        }
        
    }

}