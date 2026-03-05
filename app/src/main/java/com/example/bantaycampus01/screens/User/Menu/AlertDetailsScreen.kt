package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun UserAlertDetailsScreen(
    modifier: Modifier,
    navController: NavController,

    userName: String = "User",

    reportId: String = "Report ID: #BC-2026-0145",
    statusText: String = "RESOLVED",
    categoryText: String = "⚡ Power Outage",
    dateTimeText: String = "Jan 27, 2026 - 8:10 AM",
    locationText: String = "Academic Building - 2nd Floor",
    descriptionText: String = "Lights are out in several classrooms. Please take caution while moving around the area.",
    reportedByText: String = "Campus Security",
    activityLogs: List<String> = listOf(
        "8:10 AM – Alert sent",
        "8:12 AM – Acknowledged by Security",
        "8:20 AM – Electrician dispatched",
        "9:40 AM – Issue resolved"
    ),

    onReturn: () -> Unit = { navController.popBackStack() },
    onViewImage: () -> Unit = {}
) {
    val border = Color(0xFF6F7A8E)
    val panelBg = Color(0xFFCAD6EE)
    val header = UserUI.DarkBlue

    val statusDot = when (statusText.uppercase()) {
        "SAFE", "RESOLVED" -> UserUI.Green
        "CAUTION", "ACKNOWLEDGED" -> Color(0xFFF4B400)
        "RESTRICTED", "CRITICAL" -> Color(0xFFE53935)
        else -> UserUI.Green
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UserUI.Bg)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            UserHeader()

            TextButton(
                onClick = onReturn,
                modifier = Modifier.padding(horizontal = 14.dp)
            ) {
                Text(
                    text = "← RETURN",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .border(1.dp, border, RoundedCornerShape(14.dp))
                    .clip(RoundedCornerShape(14.dp))
                    .background(panelBg)
                    .padding(12.dp)
            ) {
                Surface(
                    color = header,
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = reportId,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(statusDot)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = statusText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = statusDot
                    )
                }

                Spacer(Modifier.height(12.dp))

                InfoRow(label = "Category:", value = categoryText)
                InfoRow(label = "Date & Time:", value = dateTimeText)
                InfoRow(label = "Location:", value = locationText)

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Description:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )
                Text(
                    text = descriptionText,
                    fontSize = 12.sp,
                    color = UserUI.DarkBlue,
                    lineHeight = 14.sp
                )

                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Attachment:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue
                    )
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = onViewImage,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.height(24.dp)
                    ) {
                        Text(
                            text = "View Image",
                            fontSize = 10.sp,
                            color = UserUI.DarkBlue,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                InfoRow(label = "Reported By:", value = reportedByText)

                Spacer(Modifier.height(12.dp))

                Divider(color = border.copy(alpha = 0.7f), thickness = 1.dp)

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "ACTIVITY LOG:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )
                Spacer(Modifier.height(8.dp))

                activityLogs.forEach {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = UserUI.DarkBlue,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = UserUI.DarkBlue,
            modifier = Modifier.width(96.dp)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = UserUI.DarkBlue,
            lineHeight = 14.sp
        )
    }
}