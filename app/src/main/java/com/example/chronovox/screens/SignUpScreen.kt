package com.example.chronovox.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chronovox.R
import com.example.chronovox.components.ClickableTextComponent
import com.example.chronovox.components.ContinueWithGoogle
import com.example.chronovox.components.DividerTextComponent
import com.example.chronovox.components.HeadingTextComponent
import com.example.chronovox.components.InputTextField
import com.example.chronovox.components.PasswordTextField
import com.example.chronovox.components.RegularButtonComponent
import com.example.chronovox.navigation.Screen
import com.example.chronovox.ui.theme.ChronoWhite

@Composable
fun SignUpScreen(
    navController: NavController
) {
    Surface(
        color = ChronoWhite,
        modifier = Modifier
            .fillMaxSize()
            .background(ChronoWhite)
            .padding(28.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            //RegularTextComponent(value = "Hey there, ")
            HeadingTextComponent(value = "Create Account")
            Spacer(modifier = Modifier.height(20.dp))
            InputTextField(labelValue = "Name", painterResource(id = R.drawable.profile))
            InputTextField(labelValue = "Email", painterResource(id = R.drawable.email))
            PasswordTextField(labelValue = "Password", painterResource(id = R.drawable.lock))
            ClickableTextComponent(
                initialText = "Already have an account? ",
                actionText = "Sign in ",
                onTextSelected = {
                    navController.navigate(route = Screen.SignIn.route)
                })

            RegularButtonComponent(value = "Create account", onButtonClicked = { /*TODO*/ })

            DividerTextComponent()

            ContinueWithGoogle(text = "Sign up with Google") {

            }

        }

    }
}
