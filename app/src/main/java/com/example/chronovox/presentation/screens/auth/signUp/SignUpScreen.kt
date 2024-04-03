package com.example.chronovox.presentation.screens.auth.signUp

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


                Image(
                    painter = painterResource(id = R.drawable.chrono_logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(200.dp)
                        .padding( 16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                HeadingTextComponent(value = "Create Account")
                Spacer(modifier = Modifier.height(20.dp))
                val signUpUiState = signUpViewModel.signUpUiState.value

                InputTextField(
                    labelValue = "Name",
                    painterResource(id = R.drawable.profile),
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.NameChanged(it))
                    },
                    errorStatus = signUpUiState.nameError,
                    errorMessage = if (signUpUiState.nameError) "Name cannot be blank" else null
                )
                InputTextField(
                    labelValue = "Email",
                    painterResource(id = R.drawable.email),
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.EmailChanged(it))
                    },
                    errorStatus = signUpUiState.emailError,
                    errorMessage = if (signUpUiState.emailError) "Enter a valid email" else null
                )
                PasswordTextField(
                    labelValue = "Password",
                    painterResource(id = R.drawable.lock),
                    onTextSelected = {
                        signUpViewModel.onEvent(SignUpUiEvent.PasswordChanged(it))
                    },
                    errorStatus = signUpUiState.passwordError,
                    errorMessage = if (signUpUiState.passwordError) "Password must be at least 8 characters, containing at least one uppercase letter and one number" else null
                )
                ClickableTextComponent(
                    initialText = "Already have an account? ",
                    actionText = "Sign in ",
                    onTextSelected = {
                        navController.navigate(route = Screen.SignIn.route)
                    })

                RegularButtonComponent(
                    value = "Create account",
                    onButtonClicked = {
                        // Trigger validation for each field
                        signUpViewModel.onEvent(SignUpUiEvent.NameChanged(signUpViewModel.signUpUiState.value.name))
                        signUpViewModel.onEvent(SignUpUiEvent.EmailChanged(signUpViewModel.signUpUiState.value.email))
                        signUpViewModel.onEvent(SignUpUiEvent.PasswordChanged(signUpViewModel.signUpUiState.value.password))

                        // Check if any field has an error
                        val hasError = signUpViewModel.signUpUiState.value.nameError ||
                                signUpViewModel.signUpUiState.value.emailError ||
                                signUpViewModel.signUpUiState.value.passwordError

                        // If any field has an error, do not proceed with account creation
                        if (!hasError) {
                            signUpViewModel.onEvent(SignUpUiEvent.CreateAccountButtonClicked)
                            signUpViewModel.navigateToHome = {
                                navController.navigate(route = Screen.Home.route)
                            }
                        }
                    },
                    isEnabled = true,
                    errorMessages = listOfNotNull(
                        if (signUpUiState.nameError) "Name cannot be blank" else null,
                        if (signUpUiState.emailError) "Enter a valid email" else null,
                        if (signUpUiState.passwordError) "Password must be at least 8 characters, containing at least one uppercase letter and one number" else null
                    ) // Remove null entries from the list
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


