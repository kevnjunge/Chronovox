package com.example.chronovox.presentation.screens.auth.signIn

data class SignInUiState (
    var email: String = "",
    var password:String = "",

    var emailError :Boolean = false,
    var passwordError : Boolean = false,
)