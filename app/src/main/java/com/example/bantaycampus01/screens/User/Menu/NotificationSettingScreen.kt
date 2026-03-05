package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserBottomNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // Same palette feel as your screenshot / AccountInfo table
    val tableBg = Color(0xFFC7D2E8)
    val tableBorder = Color(0xFF6F7A8E)
    val textColor = Color(0xFF22304A)

    // local states (UI only for now)
    var emergencyAlerts by remember { mutableStateOf(true) }
    var statusUpdates by remember { mutableStateOf(true) }
    var riskLevelUpdates by remember { mutableStateOf(true) }
    var soundVibration by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserTopBar(
                title = "",
                showReturn = true,
                onReturn = { navController.popBackStack() }
            )
        },
        bottomBar = {
            UserBottomNavBar(
                onHome = { navController.navigate("UserHomePage_Screen") },
                onShield = { navController.navigate("UserSafety_Screen") },
                onSos = { /* TODO: navController.navigate("UserSos_Screen") */ },
                onAlert = { navController.navigate("UserAlert_Screen") },
                onProfile = { navController.navigate("UserProfile_Screen") }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 12.dp, bottom = 110.dp)
        ) {
            Text(
                text = "Notification Preference",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = UserUI.DarkBlue,
                modifier = Modifier.padding(start = 2.dp)
            )

            Spacer(Modifier.height(10.dp))

            // ✅ Bordered table like screenshot
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(tableBg, RoundedCornerShape(2.dp))
                    .border(1.dp, tableBorder, RoundedCornerShape(2.dp))
            ) {
                PreferenceRow(
                    title = "Emergency Alerts",
                    desc = "Receive notifications when there is an active incident on campus.",
                    checked = emergencyAlerts,
                    onCheckedChange = { emergencyAlerts = it },
                    dividerColor = tableBorder,
                    textColor = textColor
                )

                PreferenceRow(
                    title = "Status Updates",
                    desc = "Notify when your report changes status.",
                    checked = statusUpdates,
                    onCheckedChange = { statusUpdates = it },
                    dividerColor = tableBorder,
                    textColor = textColor
                )

                PreferenceRow(
                    title = "Campus Risk Level Updates",
                    desc = "Notify when campus status changes.",
                    checked = riskLevelUpdates,
                    onCheckedChange = { riskLevelUpdates = it },
                    dividerColor = tableBorder,
                    textColor = textColor
                )

                PreferenceRow(
                    title = "Sound & Vibration",
                    desc = "Enable alert sound and vibration for emergencies.",
                    checked = soundVibration,
                    onCheckedChange = { soundVibration = it },
                    dividerColor = tableBorder,
                    textColor = textColor
                )
            }
        }
    }
}

@Composable
private fun PreferenceRow(
    title: String,
    desc: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    dividerColor: Color,
    textColor: Color,
    isLast: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = desc,
                    fontSize = 11.sp,
                    color = textColor.copy(alpha = 0.9f),
                    lineHeight = 13.sp
                )
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }

        if (!isLast) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(dividerColor)
            )
        }
    }
}