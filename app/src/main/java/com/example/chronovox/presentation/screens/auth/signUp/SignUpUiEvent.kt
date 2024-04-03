package com.example.chronovox.presentation.screens.auth.signUp

 sealed class SignUpUiEvent {

     data class NameChanged(val name: String): SignUpUiEvent()

     data class EmailChanged(val email: String): SignUpUiEvent()

     data class PasswordChanged(val password: String): SignUpUiEvent()

     object CreateAccountButtonClicked: SignUpUiEvent()
}