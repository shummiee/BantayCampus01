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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

enum class NotificationCode {
    ALERT,
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
    val isQuoteSubtitle: Boolean = false,
    val updatedAt: Long = 0L
)

@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    userName: String = "User"
) {
    val db = remember { FirebaseFirestore.getInstance() }

    var notifications by rememberSaveable { mutableStateOf(listOf<NotificationItem>()) }

    DisposableEffect(Unit) {
        var advisoryItem: NotificationItem? = null
        var statusItem: NotificationItem? = null

        fun rebuildList() {
            notifications = listOfNotNull(statusItem, advisoryItem)
                .sortedByDescending { it.updatedAt }
        }

        val statusListener: ListenerRegistration =
            db.collection("campus_updates")
                .document("status")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener

                    if (snapshot != null && snapshot.exists()) {
                        val status = snapshot.getString("status") ?: "SAFE"
                        val message = snapshot.getString("message")
                            ?: "Campus status has been updated."
                        val updatedAt = snapshot.getLong("updatedAt") ?: 0L

                        val dotColor = when (status.uppercase()) {
                            "SAFE", "RESOLVED" -> UserUI.Green
                            "CAUTION" -> Color(0xFFF4B400)
                            "RESTRICTED" -> Color(0xFFE53935)
                            else -> UserUI.Green
                        }

                        statusItem = NotificationItem(
                            code = NotificationCode.ALERT,
                            time = formatTimeAgo(updatedAt),
                            title = "Campus Alert",
                            subtitle = message,
                            statusText = "Status: $status",
                            statusDot = dotColor,
                            leading = "🚨",
                            isQuoteSubtitle = false,
                            updatedAt = updatedAt
                        )
                    } else {
                        statusItem = null
                    }

                    rebuildList()
                }

        val advisoryListener: ListenerRegistration =
            db.collection("campus_updates")
                .document("advisory")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener

                    if (snapshot != null && snapshot.exists()) {
                        val message = snapshot.getString("message")
                            ?: "There is a new campus announcement."
                        val updatedAt = snapshot.getLong("updatedAt") ?: 0L

                        advisoryItem = NotificationItem(
                            code = NotificationCode.ANNOUNCEMENT,
                            time = formatTimeAgo(updatedAt),
                            title = "New Announcement",
                            subtitle = "“$message”",
                            leading = "📣",
                            isQuoteSubtitle = true,
                            updatedAt = updatedAt
                        )
                    } else {
                        advisoryItem = null
                    }

                    rebuildList()
                }

        onDispose {
            statusListener.remove()
            advisoryListener.remove()
        }
    }

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserHeader()
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

            if (notifications.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            UserUI.DarkBlue.copy(alpha = 0.25f),
                            RoundedCornerShape(14.dp)
                        ),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE6E6E6)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No notifications yet.",
                            fontSize = 14.sp,
                            color = UserUI.DarkBlue
                        )
                    }
                }
            } else {
                notifications.forEach { item ->
                    NotificationRow(
                        item = item,
                        onClick = {
                            navController.navigate("UserSafety_Screen")
                        }
                    )
                }
            }
        }
    }
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

private fun formatTimeAgo(timestamp: Long): String {
    if (timestamp <= 0L) return "Just now"

    val diffMillis = System.currentTimeMillis() - timestamp
    val seconds = diffMillis / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "Just now"
        minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
        hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
        days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
        else -> "A while ago"
    }
}