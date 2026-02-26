package com.example.bantaycampus01.partials.user

import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.finalproject.navigation.Routes
import com.example.bantaycampus01.screens.User.*

fun NavGraphBuilder.userNavGraph(navController: NavController) {
    navigation(
        startDestination = Routes.USER_DASHBOARD,
        route = Routes.USER_GRAPH
    ) {

        composable(Routes.USER_DASHBOARD) {
            UserHomePage(
                onSendReport = { navController.navigate(Routes.USER_SEND_REPORT) },
                onReportHistory = { navController.navigate(Routes.USER_REPORT_HISTORY) },
                onReportStatus = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onNotifications = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) },
                onSos = { navController.navigate(Routes.USER_SOS) }
            )

        }

        composable(Routes.USER_SEND_REPORT) {
            SendReportScreen(
                onClose = { navController.popBackStack() }
            )
        }

        composable(Routes.USER_REPORT_HISTORY) {
            ReportHistoryScreen(
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onSos = { navController.navigate(Routes.USER_SOS) }, // ✅ SOS popup
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) },
                onAddDetails = { navController.navigate(Routes.USER_SEND_REPORT) }
            )
        }

        composable(Routes.USER_REPORT_STATUS) {
            ReportStatusScreen(
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { /* already here */ },
                onSos = { navController.navigate(Routes.USER_SOS) }, // ✅ SOS popup
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) }
            )
        }

        composable(Routes.USER_NOTIFICATIONS) {
            NotificationsScreen(
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onSos = { navController.navigate(Routes.USER_SOS) }, // ✅ SOS popup
                onAlert = { /* already here */ },
                onProfile = { navController.navigate(Routes.USER_PROFILE) },
                onViewDetails = { navController.navigate(Routes.USER_ALERT_DETAILS) }
            )
        }

        composable(Routes.USER_ALERT_DETAILS) {
            AlertDetailsScreen(
                onReturn = { navController.popBackStack() },
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onSos = { navController.navigate(Routes.USER_SOS) }, // ✅ SOS popup
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) },
                onAddDetails = { navController.navigate(Routes.USER_SEND_REPORT) }
            )
        }

        composable(Routes.USER_PROFILE) {
            ProfileScreen(
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { /* already here */ },
                onAddDetails = { navController.navigate(Routes.USER_SEND_REPORT) },
                onAccountInfo = { navController.navigate(Routes.USER_ACCOUNT_INFO) },
                onSchoolContacts = { navController.navigate(Routes.USER_SCHOOL_CONTACTS) },
                onNotificationSettings = { navController.navigate(Routes.USER_NOTIFICATION_SETTINGS) },

                onAboutUs = { navController.navigate(Routes.USER_ABOUT_US) },

                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.USER_GRAPH) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )

        }

        composable(Routes.USER_ACCOUNT_INFO) {
            AccountInfoScreen(
                onReturn = { navController.popBackStack() },
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onSos = { navController.navigate(Routes.USER_SOS) }, // ✅ SOS popup
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) },
                onAddDetails = { navController.navigate(Routes.USER_SEND_REPORT) }
            )
        }

        composable(Routes.USER_SCHOOL_CONTACTS) {
            SchoolContactsScreen(
                onReturn = { navController.popBackStack() },
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onSos = { navController.navigate(Routes.USER_SOS) }, // ✅ SOS popup
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) },
                onAddDetails = { navController.navigate(Routes.USER_SEND_REPORT) }
            )
        }

        composable(Routes.USER_ABOUT_US) {
            AboutUsScreen(
                onReturn = { navController.popBackStack() },
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onSos = { navController.navigate(Routes.USER_SOS) },
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) }
            )
        }


        composable(Routes.USER_NOTIFICATION_SETTINGS) {
            NotificationSettingsScreen(
                onReturn = { navController.popBackStack() },
                onHome = { navController.navigate(Routes.USER_DASHBOARD) },
                onShield = { navController.navigate(Routes.USER_REPORT_STATUS) },
                onSos = { navController.navigate(Routes.USER_SOS) }, // ✅ SOS popup
                onAlert = { navController.navigate(Routes.USER_NOTIFICATIONS) },
                onProfile = { navController.navigate(Routes.USER_PROFILE) },
                onAddDetails = { navController.navigate(Routes.USER_SEND_REPORT) }
            )
        }

        // ✅ NEW: SOS route = popup flow
        composable(Routes.USER_SOS) {
            SosPopupRoute(
                onDone = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun SosPopupRoute(onDone: () -> Unit) {
    var showConfirm by remember { mutableStateOf(true) }
    var showSent by remember { mutableStateOf(false) }

    if (showConfirm) {
        SosConfirmDialog(
            onClose = {
                showConfirm = false
                onDone()
            },
            onConfirm = {
                showConfirm = false
                showSent = true
            }
        )
    }

    if (showSent) {
        SosSentDialog(
            onClose = {
                showSent = false
                onDone()
            }
        )
    }
}
