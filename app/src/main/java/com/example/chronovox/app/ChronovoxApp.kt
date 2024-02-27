package com.example.chronovox.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chronovox.screens.SignInScreen

@Composable
fun ChronovoxApp(){
    Surface(
        modifier = Modifier.fillMaxSize()
    ){
       SignInScreen()
    }
}