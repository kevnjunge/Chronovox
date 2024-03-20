package com.example.chronovox.navigation

sealed class Screen(val route:String){

    object SignIn: Screen(route = "signIn_screen")
    object SignUp: Screen(route= "signUp_screen")
    object Home: Screen(route = "home_screen")
    object Detail: Screen(route = "detail_screen")

}