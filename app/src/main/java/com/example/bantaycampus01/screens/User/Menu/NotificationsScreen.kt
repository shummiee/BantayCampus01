package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.User.Menu.PopUps.MarkSafeDialog
import com.example.bantaycampus01.screens.User.Menu.PopUps.ReportDetailDialog

// ✅ "Code name" so backend can differentiate later
enum class NotificationCode {
    SUSPICIOUS_ACTIVITY,
    ANNOUNCEMENT
}

data class NotificationItem(
    val code: NotificationCode,
    val time: String,
    val title: String,
    val subtitle: String,
    val statusText: String = "",
    val statusDot: Color? = null,
    val leading: String = "🚨",
    val isQuoteSubtitle: Boolean = false
)

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userName: String = "User"
) {
    var showReportDetail by rememberSaveable { mutableStateOf(false) }

    // ✅ this is the important state (owned by the SCREEN)
    var showMarkedSafeDialog by rememberSaveable { mutableStateOf(false) }

    val notifications = remember {
        listOf(
            NotificationItem(
                code = NotificationCode.SUSPICIOUS_ACTIVITY,
                time = "1 minute ago",
                title = "Suspicious Activity",
                subtitle = "Near Library Entrance",
                statusText = "Status: Responding",
                statusDot = Color(0xFFF4B400),
                leading = "🚨"
            ),
            NotificationItem(
                code = NotificationCode.SUSPICIOUS_ACTIVITY,
                time = "5 minutes ago",
                title = "Suspicious Activity",
                subtitle = "Near Library Entrance",
                statusText = "Status: Resolved",
                statusDot = UserUI.Green,
                leading = "🚨"
            ),
            NotificationItem(
                code = NotificationCode.ANNOUNCEMENT,
                time = "25 minutes ago",
                title = "New Announcement",
                subtitle = "“There’s an chuchuchu...”",
                leading = "📣",
                isQuoteSubtitle = true
            )
        )
    }

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserHeader(
                userName = userName,
                onProfileClick = { navController.navigate("UserProfile_Screen") }
            )
        },
        bottomBar = {
            UserNavBar(modifier = Modifier, navController = navController)
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 14.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "NOTIFICATION",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )

            notifications.forEach { item ->
                NotificationRow(
                    item = item,
                    onClick = {
                        when (item.code) {
                            NotificationCode.SUSPICIOUS_ACTIVITY -> showReportDetail = true
                            NotificationCode.ANNOUNCEMENT -> navController.navigate("UserSafety_Screen")
                        }
                    }
                )
            }
        }
    }

    // ✅ Report detail popup
    ReportDetailDialog(
        show = showReportDetail,
        reportIdLabel = "Report ID: #BC-2026-0145",
        statusLabel = "Responding",
        category = "🚨 Suspicious Activity",
        dateTime = "Feb 3, 2026 – 9:23AM",
        location = "Near Library Entrance",
        description = "There is a person acting suspiciously near the stairs, checking doors and following students.",
        hasAttachment = true,
        onViewAttachment = { /* TODO */ },

        // ✅ where you will connect backend later:
        onMarkSafe = {
            // TODO: update backend + local UI state
        },

        // ✅ show thank-you dialog AFTER closing report dialog
        onShowMarkedSafe = { showMarkedSafeDialog = true },

        onDismiss = { showReportDetail = false }
    )

    // ✅ Thank-you dialog (this is what your screenshot shows)
    MarkSafeDialog(
        show = showMarkedSafeDialog,
        onConfirm = { showMarkedSafeDialog = false }, // CLOSE
        onDismiss = { showMarkedSafeDialog = false }
    )
}

@Composable
private fun NotificationRow(
    item: NotificationItem,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(14.dp)
    val borderColor = UserUI.DarkBlue.copy(alpha = 0.55f)
    val cardBg = Color(0xFFE6E6E6)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, shape)
            .clickable { onClick() },
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item.leading, fontSize = 14.sp)
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        color = UserUI.DarkBlue
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = item.time,
                        fontSize = 11.sp,
                        fontStyle = FontStyle.Italic,
                        color = UserUI.DarkBlue.copy(alpha = 0.70f)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!item.isQuoteSubtitle) {
                        Text(text = "📍", fontSize = 12.sp)
                        Spacer(Modifier.width(6.dp))
                    }
                    Text(
                        text = item.subtitle,
                        fontSize = 12.sp,
                        fontWeight = if (item.isQuoteSubtitle) FontWeight.Medium else FontWeight.SemiBold,
                        fontStyle = if (item.isQuoteSubtitle) FontStyle.Italic else FontStyle.Normal,
                        color = UserUI.DarkBlue
                    )
                }

                if (item.statusText.isNotBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = item.statusText,
                            fontSize = 12.sp,
                            color = UserUI.DarkBlue
                        )
                        if (item.statusDot != null) {
                            Spacer(Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(item.statusDot, RoundedCornerShape(50))
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(10.dp))
            Text(
                text = "→",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )
        }
    }
}