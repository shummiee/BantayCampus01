package com.example.bantaycampus01

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bantaycampus01.screens.Admin.AdminAlertHistory
import com.example.bantaycampus01.screens.Admin.AdminAlertPage
import com.example.bantaycampus01.screens.Admin.AdminAlertPageDetails
import com.example.bantaycampus01.screens.Admin.AdminCheckInPage
import com.example.bantaycampus01.screens.Admin.AdminHomePage
import com.example.bantaycampus01.screens.Admin.AdminProfileScreen
import com.example.bantaycampus01.screens.Admin.AdminSafetyPage
import com.example.bantaycampus01.screens.LoginScreen
import com.example.bantaycampus01.screens.RegistrationScreen
import com.example.bantaycampus01.screens.User.Menu.AboutUsScreen
import com.example.bantaycampus01.screens.User.Menu.AccountInfoScreen
import com.example.bantaycampus01.screens.User.Menu.NotificationSettingsScreen
import com.example.bantaycampus01.screens.User.Menu.ReportStatusScreen
import com.example.bantaycampus01.screens.User.UserAlertDetailsScreen
import com.example.bantaycampus01.screens.User.UserAlertScreen
import com.example.bantaycampus01.screens.User.UserHomePage
import com.example.bantaycampus01.screens.User.UserProfileScreen
import com.example.bantaycampus01.screens.User.UserSafetyScreen
import com.example.bantaycampus01.screens.User.ReportHistoryScreen
import com.example.bantaycampus01.screens.User.Menu.NotificationsScreen
import com.example.bantaycampus01.screens.User.Menu.SchoolContactsScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier){
    val navController = rememberNavController()

    val isLoggedIn = Firebase.auth.currentUser!=null
    val firstPage = if(isLoggedIn) "MainMenu_Screen" else "Login_Screen"

    NavHost(navController = navController, startDestination = "Login_Screen"){

        composable("Login_Screen"){
            LoginScreen(modifier, navController)
        }
        composable("Registration_Screen"){
            RegistrationScreen(modifier, navController)
        }
        //*******************************ADMIN*******************************
        composable("AdminHomePage_Screen") {
            AdminHomePage(modifier,navController)
        }
        composable("AdminSafetyPage_Screen"){
            AdminSafetyPage(modifier,navController)
        }
        composable("AdminAlertPage_Screen"){
            AdminAlertPage(modifier,navController)
        }
        composable("AdminCheckinPage_Screen"){
            AdminCheckInPage(modifier,navController)
        }
        composable("AdminAlertPageDetails_Screen"){
            AdminAlertPageDetails(modifier, navController)
        }
        composable("AdminAlertHistory_Screen"){
            AdminAlertHistory(modifier, navController)
        }
        composable("AdminProfile_Screen"){
            AdminProfileScreen(modifier, navController)
        }
        //********************************USER******************************
        composable("UserHomePage_Screen"){
            UserHomePage(modifier, navController)
        }
        composable("UserProfile_Screen"){
            UserProfileScreen(modifier, navController)
        }
        composable("UserSafety_Screen"){
            UserSafetyScreen(modifier, navController)
        }
        composable("UserAlert_Screen"){
            UserAlertScreen(modifier, navController)
        }
        //*************************************USER DETAILS SCREENS***************
        composable("UserAlertDetails_Screen"){
            UserAlertDetailsScreen(modifier, navController) { }
        }
        composable("UserSend_Screen"){
            UserAlertDetailsScreen(modifier, navController) { }
        }
        composable("UserReportHistory_Screen"){
            ReportHistoryScreen(modifier, navController)
        }
        composable("UserNotification_Screen"){
            NotificationsScreen(modifier, navController)
        }
        composable("AccountInfo_Screen"){
            AccountInfoScreen(modifier, navController)
        }
        composable("SchoolContact_Screen"){
            SchoolContactsScreen(modifier, navController)
        }
        composable("NotificationSetting_Screen"){
            NotificationSettingsScreen(modifier, navController)
        }
        composable("AboutUs_Screen"){
            AboutUsScreen(modifier, navController)
        }
    }
}