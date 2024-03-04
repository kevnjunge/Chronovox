package com.example.chronovox.presentation.screens.signUp

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.chronovox.util.FormValidation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class SignUpViewModel : ViewModel() {
    private val TAG = SignUpViewModel::class.simpleName

    var signUpUiState = mutableStateOf(SignUpUiState())

    var allValidationsPass = mutableStateOf(false)

    var signUpInProgress = mutableStateOf(false)

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

    var navigateToHome: (() -> Unit)? = null
    private fun signUp() {
        Log.d(TAG, "Sign Up Button ")
        printState()

        createUserAccount(
            email = signUpUiState.value.email,
            password = signUpUiState.value.password,
        )
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
        Log.d(TAG, "NameResult= $nameResult")
        Log.d(TAG, "emailResult= $emailResult")
        Log.d(TAG, "passwordResult= $passwordResult")

        signUpUiState.value = signUpUiState.value.copy(
            nameError = nameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPass.value = nameResult.status && emailResult.status && passwordResult.status
    }

    private fun createUserAccount(email: String, password: String) {

        signUpInProgress.value = true

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d(TAG, "Inside OnCompleteListener")
                Log.d(TAG, "is Successful = ${it.isSuccessful}")
                signUpInProgress.value = false
                navigateToHome?.invoke()
            }
            .addOnFailureListener {
                Log.d(TAG, "Inside OnFailureListener")
                Log.d(TAG, "Exception = ${it.message}")
                Log.d(TAG, "Exception = ${it.localizedMessage}")
            }

    }
    var navigateToSignIn: (() -> Unit)? = null
    fun logout(){
        val firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signOut()

        val authStateListener = AuthStateListener{
            if (it.currentUser == null){
                Log.d(TAG, "Inside Sign out success")
                navigateToSignIn?.invoke()
            }else{
                Log.d(TAG, "sign out not complete")
            }
        }

        firebaseAuth.addAuthStateListener(authStateListener)
    }

}