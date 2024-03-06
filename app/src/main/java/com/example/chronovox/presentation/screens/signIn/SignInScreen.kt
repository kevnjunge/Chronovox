package com.example.chronovox.presentation.screens.signIn

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.chronovox.R
import com.example.chronovox.navigation.Screen
import com.example.chronovox.presentation.components.ClickableTextComponent
import com.example.chronovox.presentation.components.ContinueWithGoogle
import com.example.chronovox.presentation.components.DividerTextComponent
import com.example.chronovox.presentation.components.HeadingTextComponent
import com.example.chronovox.presentation.components.InputTextField
import com.example.chronovox.presentation.components.PasswordTextField
import com.example.chronovox.presentation.components.RegularButtonComponent
import com.example.chronovox.theme.ChronoWhite

@Composable
fun SignInScreen(
    navController: NavController,
    signInViewModel: SignInViewModel = viewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = ChronoWhite,
            modifier = Modifier
                .fillMaxSize()
                .background(ChronoWhite)
                .padding(28.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                HeadingTextComponent(value = "Sign in")
                Spacer(modifier = Modifier.height(20.dp))
                InputTextField(
                    labelValue = "Email",
                    painterResource(id = R.drawable.email),
                    onTextSelected = {
                        signInViewModel.onEvent(SignInUiEvent.EmailChanged(it))
                    },
                    errorStatus = signInViewModel.signInUiState.value.emailError
                )
                PasswordTextField(
                    labelValue = "Password",
                    painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        signInViewModel.onEvent(SignInUiEvent.PasswordChanged(it))
                    },
                    errorStatus = signInViewModel.signInUiState.value.passwordError
                )
                ClickableTextComponent(
                    initialText = "Don't have an account ? ",
                    actionText = "Sign up",
                    onTextSelected = { navController.navigate(route = Screen.SignUp.route) })
                RegularButtonComponent(value = "Log in", onButtonClicked = {
                    signInViewModel.onEvent(SignInUiEvent.LogInButtonClicked)

                    signInViewModel.navigateToHome = {
                        navController.navigate(route = Screen.Home.route)
                    }
                }, isEnabled = signInViewModel.allValidationPassed.value )

                DividerTextComponent()

                ContinueWithGoogle(text = "Sign in with Google") {

                }

            }

        }
        if (signInViewModel.loginInProgress.value){
            CircularProgressIndicator()
        }
    }
}