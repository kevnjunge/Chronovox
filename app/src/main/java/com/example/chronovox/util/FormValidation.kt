package com.example.chronovox.util

object FormValidation {

    fun validateName(name: String):ValidationResult{
        return ValidationResult(
            (name.isNotEmpty())
        )
    }

    fun validateEmail(email: String):ValidationResult{
       return ValidationResult(
           (email.isNotEmpty())
       )
    }

    fun validatePassword(password: String):ValidationResult{
        return ValidationResult(
            (password.isNotEmpty() && password.length >= 8)
        )
    }

}

data class ValidationResult(
    val status: Boolean = false
)