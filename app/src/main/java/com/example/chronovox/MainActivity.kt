package com.example.chronovox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.chronovox.presentation.screens.detail.DetailViewModel
import com.example.chronovox.presentation.screens.home.HomeViewModel
import com.example.chronovox.presentation.screens.signIn.SignInViewModel

class MainActivity : ComponentActivity() {

    private val signInViewModel = SignInViewModel()
    private val homeViewModel = HomeViewModel()
    private val detailViewModel = DetailViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContent {

        ChronovoxApp(signInViewModel,homeViewModel,detailViewModel)
        }
    }
}

