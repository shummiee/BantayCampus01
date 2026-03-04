package com.example.bantaycampus01.screens.User.Menu

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
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserHeaderBar
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.User.Menu.PopUps.MarkSafeDialog
import com.example.bantaycampus01.screens.User.Menu.PopUps.ReportDetailDialog

@Composable
fun ReportHistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    // Report detail dialog
    var showDetails by remember { mutableStateOf(false) }

    // ✅ success dialog
    var showMarkedSafeDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserHeaderBar(
                onProfile = { navController.navigate("UserProfile_Screen") }
            )
        },
        bottomBar = {
            UserNavBar(
                modifier = Modifier,
                navController = navController
            )
        }
    ) { padding ->

        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .background(UserUI.Bg)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

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

    // ✅ Report detail popup
    ReportDetailDialog(
        show = showDetails,

        // called when MARK SAFE is pressed
        onMarkSafe = {
            // backend logic later
        },

        // show thank-you popup
        onShowMarkedSafe = {
            showMarkedSafeDialog = true
        },

        // close the report dialog
        onDismiss = {
            showDetails = false
        }
    )

    // ✅ Thank-you popup
    MarkSafeDialog(
        show = showMarkedSafeDialog,
        onConfirm = { showMarkedSafeDialog = false },
        onDismiss = { showMarkedSafeDialog = false }
    )
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

        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

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
}