package com.example.chronovox.presentation.screens.signUp

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
fun SignUpScreen(
    navController: NavController,
    signUpViewModel: SignUpViewModel = viewModel()
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

                //RegularTextComponent(value = "Hey there, ")
                HeadingTextComponent(value = "Create Account")
                Spacer(modifier = Modifier.height(20.dp))
                InputTextField(
                    labelValue = "Name",
                    painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.NameChanged(it))
                    },
                    errorStatus = signUpViewModel.signUpUiState.value.nameError
                )
                InputTextField(
                    labelValue = "Email",
                    painterResource(id = R.drawable.email),
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.EmailChanged(it))
                    },
                    errorStatus = signUpViewModel.signUpUiState.value.emailError
                )
                PasswordTextField(
                    labelValue = "Password",
                    painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.PasswordChanged(it))
                    },
                    errorStatus = signUpViewModel.signUpUiState.value.passwordError
                )
                ClickableTextComponent(
                    initialText = "Already have an account? ",
                    actionText = "Sign in ",
                    onTextSelected = {
                        navController.navigate(route = Screen.SignIn.route)
                    })

                RegularButtonComponent(
                    value = "Create account", onButtonClicked = {
                        signUpViewModel.onEvent(SignUpUiEvent.CreateAccountButtonClicked)
                        signUpViewModel.navigateToHome = {
                            navController.navigate(route = Screen.Home.route)
                        }
                    },
                    isEnabled = signUpViewModel.allValidationsPass.value
                )

                DividerTextComponent()

                ContinueWithGoogle(text = "Sign up with Google", onclick = {

                })

            }

        }

        if (signUpViewModel.signUpInProgress.value) {
            CircularProgressIndicator()
        }
    }
}
