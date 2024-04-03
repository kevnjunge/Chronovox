package com.example.chronovox.presentation.screens.auth.signIn

sealed class SignInUiEvent {

    data class EmailChanged(val email: String): SignInUiEvent()

    data class PasswordChanged(val password: String): SignInUiEvent()

    object LogInButtonClicked: SignInUiEvent()
}