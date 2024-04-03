package com.example.chronovox.presentation.screens.auth.signUp

data class SignUpUiState (
    var name: String = "",
    var email: String = "",
    var password: String = "",

    var nameError : Boolean = false,
    var emailError :Boolean = false,
    var passwordError : Boolean = false,
)