package com.example.chronovox.util

import android.util.Patterns

object FormValidation {

    fun validateName(name: String): ValidationResult {
        return ValidationResult(
            (name.isNotEmpty())
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    fun validatePassword(password: String): ValidationResult {
        val regex = "^(?=.*[A-Z])(?=.*\\d).{8,}\$".toRegex()
        return ValidationResult(password.matches(regex))
    }

}

data class ValidationResult(
    val status: Boolean = false
)