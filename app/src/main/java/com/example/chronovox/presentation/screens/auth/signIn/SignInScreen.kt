package com.example.chronovox.presentation.screens.auth.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
                Image(
                    painter = painterResource(id = R.drawable.chrono_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                HeadingTextComponent(value = "Sign in")
                Spacer(modifier = Modifier.height(20.dp))
                InputTextField(
                    labelValue = "Email",
                    painterResource(id = R.drawable.email),
                    onTextSelected = {
                        signInViewModel.onEvent(SignInUiEvent.EmailChanged(it))
                    },
                    errorStatus = signInViewModel.signInUiState.value.emailError,
                    errorMessage = if (signInViewModel.signInUiState.value.emailError) "Enter a valid email" else null
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

                RegularButtonComponent(
                    value = "Log in",
                    onButtonClicked = {

                        signInViewModel.onEvent(SignInUiEvent.EmailChanged(signInViewModel.signInUiState.value.email))
                        signInViewModel.onEvent(SignInUiEvent.PasswordChanged(signInViewModel.signInUiState.value.password))

                        val hasError = signInViewModel.signInUiState.value.emailError ||
                                signInViewModel.signInUiState.value.passwordError

                        if (!hasError) {
                            signInViewModel.onEvent(SignInUiEvent.LogInButtonClicked)
                            signInViewModel.navigateToHome = {
                                navController.navigate(route = Screen.Home.route)
                            }
                        }

                    },
                    isEnabled = true,
                    errorMessages = listOfNotNull(
                        if (signInViewModel.signInUiState.value.emailError) "Name cannot be blank" else null,
                        if (signInViewModel.signInUiState.value.passwordError) "Enter a password" else null
                    )
                )

                DividerTextComponent()

                ContinueWithGoogle(text = "Sign in with Google") {

                }

            }

        }
        if (signInViewModel.loginInProgress.value) {
            CircularProgressIndicator()
        }
    }
}