package com.example.chronovox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.chronovox.presentation.screens.signIn.SignInViewModel

class MainActivity : ComponentActivity() {

    private val signInViewModel = SignInViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {

        ChronovoxApp(signInViewModel)
        }
    }
}

