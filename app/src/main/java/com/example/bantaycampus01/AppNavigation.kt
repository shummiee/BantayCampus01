package com.example.bantaycampus01

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bantaycampus01.screens.LoginScreen
import com.example.bantaycampus01.screens.RegistrationScreen
import com.example.bantaycampus01.screens.User.MainMenu

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login_Screen"){

        composable("Login_Screen"){
            LoginScreen(modifier, navController)
        }
        composable("Registration_Screen"){
            RegistrationScreen(modifier, navController)
        }
        composable("MainMenu_Screen") {
            MainMenu(modifier,navController)
        }
    }
}