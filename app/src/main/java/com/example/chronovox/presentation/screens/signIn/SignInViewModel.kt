package com.example.chronovox.presentation.screens.signIn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chronovox.util.FormValidation

class SignInViewModel : ViewModel() {
    var signInUiState = mutableStateOf(SignInUiState())

    fun onEvent(event: SignInUiEvent) {
        when (event) {
            is SignInUiEvent.EmailChanged -> {
                signInUiState.value = signInUiState.value.copy(
                    email = event.email
                )
            }

            is SignInUiEvent.PasswordChanged -> {
                signInUiState.value = signInUiState.value.copy(
                    password = event.password
                )
            }

            is SignInUiEvent.LogInButtonClicked -> {
                login()
            }
        }
        validateInputData()
    }

    private fun login() {
        validateInputData()
    }

    private fun validateInputData() {
        val emailResult = FormValidation.validateEmail(
            email = signInUiState.value.email
        )
        val passwordResult = FormValidation.validatePassword(
            password = signInUiState.value.password
        )

        signInUiState.value = signInUiState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
    }

}