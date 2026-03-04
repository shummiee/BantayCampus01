/*package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.*

@Composable
fun ReportHistoryScreen(
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,
    onAlert: () -> Unit,
    onProfile: () -> Unit,
    onAddDetails: () -> Unit
) {
    var showDetails by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = { UserHeaderBar(onProfile = onProfile) },
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
                .background(UserUI.Bg)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // timestamps like in wireframe p.11
            HistoryItem(
                timeAgo = "3 weeks ago",
                title = "🚨 Suspicious Activity",
                location = "📍 Near Library Entrance",
                status = "Status: Resolved 🟢",
                onClick = { showDetails = true }
            )

            HistoryItem(
                timeAgo = "1 week ago",
                title = "🚨 Suspicious Activity",
                location = "📍 Near Library Entrance",
                status = "Status: Resolved 🟢",
                onClick = { showDetails = true }
            )

            HistoryItem(
                timeAgo = "1 minute ago",
                title = "🚨 Suspicious Activity",
                location = "📍 Near Library Entrance",
                status = "Status: Responding 🟠",
                onClick = { showDetails = true }
            )
        }
    }

    if (showDetails) {
        ReportDetailDialog(
            onClose = { showDetails = false },
            onAddDetails = onAddDetails
        )
    }
}

@Composable
private fun HistoryItem(
    timeAgo: String,
    title: String,
    location: String,
    status: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = UserUI.LightCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = timeAgo,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = UserUI.DarkBlue
            )

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = location,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = UserUI.DarkBlue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = status,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = UserUI.DarkBlue
            )
        }
    }
}*/
