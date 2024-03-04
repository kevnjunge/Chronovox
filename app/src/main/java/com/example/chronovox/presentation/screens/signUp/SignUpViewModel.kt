package com.example.chronovox.presentation.screens.signUp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chronovox.util.FormValidation

class SignUpViewModel : ViewModel() {
    private val TAG = SignUpViewModel::class.simpleName
    var signUpUiState = mutableStateOf(SignUpUiState())

    fun onEvent(event: SignUpUiEvent) {
        validateInputData()
        when (event) {
            is SignUpUiEvent.NameChanged -> {
                signUpUiState.value = signUpUiState.value.copy(
                    name = event.name
                )
                printState()
            }

            is SignUpUiEvent.EmailChanged -> {
                signUpUiState.value = signUpUiState.value.copy(
                    email = event.email
                )
                printState()
            }

            is SignUpUiEvent.PasswordChanged -> {
                signUpUiState.value = signUpUiState.value.copy(
                    password = event.password
                )
                printState()
            }

            is SignUpUiEvent.CreateAccountButtonClicked -> {
                signUp()
            }
        }
    }

    private fun printState() {
        Log.d(TAG, "Inside_printState")
        Log.d(TAG, signUpUiState.value.toString())
    }

    private fun signUp() {
        Log.d(TAG, "Sign Up Button ")
        printState()

        validateInputData()
    }

    private fun validateInputData() {
        val nameResult = FormValidation.validateName(
            name = signUpUiState.value.name
        )
        val emailResult = FormValidation.validateEmail(
            email = signUpUiState.value.email
        )
        val passwordResult = FormValidation.validatePassword(
            password = signUpUiState.value.password
        )

        Log.d(TAG, "Validate Input Data ")
        Log.d(TAG, "lNameResult= $nameResult")
        Log.d(TAG, "emailResult= $emailResult")
        Log.d(TAG, "passwordResult= $passwordResult")

        signUpUiState.value = signUpUiState.value.copy(
            nameError = nameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
    }

}