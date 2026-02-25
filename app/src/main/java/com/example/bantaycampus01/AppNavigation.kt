package com.example.bantaycampus01

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bantaycampus01.screens.Admin.AdminAlertHistory
import com.example.bantaycampus01.screens.Admin.AdminCheckInPage
import com.example.bantaycampus01.screens.Admin.AdminHomePage
import com.example.bantaycampus01.screens.Admin.AdminSafetyPage
import com.example.bantaycampus01.screens.LoginScreen
import com.example.bantaycampus01.screens.RegistrationScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    val isLoggedIn = Firebase.auth.currentUser!=null
    val firstPage = if(isLoggedIn) "MainMenu_Screen" else "Login_Screen" //Login State (Remember Logged-In user after clicking home screen button on phone.)

    NavHost(navController = navController, startDestination = "Login_Screen"){

        composable("Login_Screen"){
            LoginScreen(modifier, navController)
        }
        composable("Registration_Screen"){
            RegistrationScreen(modifier, navController)
        }
        composable("AdminHomePage_Screen") {
            AdminHomePage(modifier,navController)
        }
        composable("AdminSafetyPage_Screen"){
            AdminSafetyPage(modifier,navController)
        }
        composable("AdminAlertPage_Screen"){
            AdminAlertHistory(modifier,navController)
        }
        composable("AdminCheckinPage_Screen"){
            AdminCheckInPage(modifier,navController)
        }
    }
}