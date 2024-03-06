package com.example.chronovox.presentation.screens.signIn

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chronovox.util.FormValidation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModel : ViewModel() {

    var signInUiState = mutableStateOf(SignInUiState())

    var allValidationPassed = mutableStateOf(false)

    var loginInProgress = mutableStateOf(false)

    private val _isUserLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    private var sessionChecked = false

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

    var navigateToHome: (() -> Unit)? = null
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

        allValidationPassed.value = emailResult.status && passwordResult.status
    }

    fun checkActiveSession(): Boolean {
        if (!sessionChecked) {

            if (FirebaseAuth.getInstance().currentUser != null) {
                _isUserLoggedIn.value = true
            } else {
                _isUserLoggedIn.value = false
            }
            sessionChecked = true
        }
        return _isUserLoggedIn.value
    }


    private fun login() {

        loginInProgress.value = true
        val email = signInUiState.value.email
        val password = signInUiState.value.password

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    loginInProgress.value = false
                    navigateToHome?.invoke()

                }
            }
            .addOnFailureListener {

                loginInProgress.value = false


            }

    }

}