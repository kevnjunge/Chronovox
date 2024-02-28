package com.example.chronovox

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chronovox.navigation.SetUpNavGraph

@Composable
fun ChronovoxApp(){
    lateinit var navController: NavHostController
    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        navController = rememberNavController()
        SetUpNavGraph(navController = navController)

    }
}