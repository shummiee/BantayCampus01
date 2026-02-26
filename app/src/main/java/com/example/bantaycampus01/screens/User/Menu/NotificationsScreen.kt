package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.*

@Composable
fun NotificationsScreen(
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,
    onAlert: () -> Unit,
    onProfile: () -> Unit,
    onViewDetails: () -> Unit
) {
    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            // Wireframe uses the "Hi User" header
            UserHeaderBar(onProfile = onProfile)
        },
        bottomBar = {
            UserBottomNavBar(
                onHome = onHome,
                onShield = onShield,
                onSos = onSos,
                onAlert = onAlert,
                onProfile = onProfile
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 14.dp, bottom = 110.dp)
        ) {
            Text(
                text = "NOTIFICATION",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )

            Spacer(Modifier.height(14.dp))

            NotificationRow(
                title = "Suspicious Activity",
                subtitle = "Near Library Entrance",
                statusText = "Status: Responding",
                statusDot = UserUI.DangerRed,
                time = "1 minute ago",
                onClick = onViewDetails
            )

            Spacer(Modifier.height(12.dp))

            NotificationRow(
                title = "Suspicious Activity",
                subtitle = "Near Library Entrance",
                statusText = "Status: Resolved",
                statusDot = UserUI.Green,
                time = "5 minutes ago",
                onClick = onViewDetails
            )

            Spacer(Modifier.height(12.dp))

            NotificationRow(
                title = "New Announcement",
                subtitle = "There’s an chuchuchu...",
                statusText = "",
                statusDot = null,
                time = "25 minutes ago",
                onClick = onViewDetails
            )
        }
    }
}

@Composable
private fun NotificationRow(
    title: String,
    subtitle: String,
    statusText: String,
    statusDot: Color?,
    time: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("📍", fontSize = 12.sp)
                    Spacer(Modifier.width(6.dp))
                    Text(title, fontWeight = FontWeight.Black, fontSize = 12.sp, color = UserUI.DarkBlue)
                    Spacer(Modifier.weight(1f))
                    Text(time, fontSize = 10.sp, color = UserUI.DarkBlue.copy(alpha = 0.65f))
                }

                Spacer(Modifier.height(4.dp))
                Text(subtitle, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = UserUI.DarkBlue)

                if (statusText.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(statusText, fontSize = 10.sp, color = UserUI.DarkBlue.copy(alpha = 0.8f))
                        if (statusDot != null) {
                            Spacer(Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(statusDot, RoundedCornerShape(50))
                            )
                        }
                    }
                }
            }

            Text("→", fontSize = 18.sp, fontWeight = FontWeight.Black, color = UserUI.DarkBlue)
        }
    }
}
