package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bantaycampus01.partials.user.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationSettingsScreen(
    onReturn: () -> Unit,
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,            // ✅ ADD THIS
    onAlert: () -> Unit,
    onProfile: () -> Unit,
    onAddDetails: () -> Unit
) {
    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserTopBar(
                title = "Notification Settings",
                showReturn = true,
                onReturn = onReturn
            )
        },
        bottomBar = {
            UserBottomNavBar(
                onHome = onHome,
                onShield = onShield,
                onSos = onSos,      // ✅ USE onSos
                onAlert = onAlert,
                onProfile = onProfile
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Push Notifications", fontWeight = FontWeight.Bold, color = UserUI.DarkBlue)
                Spacer(Modifier.weight(1f))
                Switch(checked = true, onCheckedChange = {})
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("SMS Alerts", fontWeight = FontWeight.Bold, color = UserUI.DarkBlue)
                Spacer(Modifier.weight(1f))
                Switch(checked = true, onCheckedChange = {})
            }
        }
    }
}
