package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.*

@Composable
fun AdminAlertPageDetails(
    modifier: Modifier,
    navController: NavController,

    adminName: String = "Admin",
    reportId: String = "Report ID: #BC-2026-0145",
    statusText: String = "RESOLVED",
    categoryText: String = "🚨 Suspicious Activity",
    dateTimeText: String = "Feb 3, 2026 - 9:23AM",
    locationText: String = "Near Library Entrance",
    descriptionText: String = "There is a person acting suspiciously near the stairs, checking doors and following students.",
    reportedByText: String = "Christina Sevilla",
    activityLogs: List<String> = listOf(
        "9:23 AM – Alert sent",
        "9:24 AM – Acknowledged by Security",
        "9:26 AM – Guard dispatched",
        "9:40 AM – Issue resolved"
    ),
    onReturn: () -> Unit = {},
    onViewImage: () -> Unit = {},
    onEdit: () -> Unit = {},
    onBack: () -> Unit = {},
    onHomeNav: () -> Unit = {},
    onAlertNav: () -> Unit = {},
    onIncomingNav: () -> Unit = {},
    onSafetyNav: () -> Unit = {},
    onProfileNav: () -> Unit = {}
) {
    val border = Color(0xFF6F7A8E)
    val panelBg = Color(0xFFCAD6EE)
    val header = DarkGrayBlue
    val green = Color(0xFF29C65E)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            AdminHeader(adminName = adminName)

            TextButton(
                onClick = onReturn,
                modifier = Modifier.padding(horizontal = 14.dp)
            ) {
                Text(
                    text = "← RETURN",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite
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
                    color = header
                ) {
                    Text(
                        text = reportId,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = White
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = TextOnWhite
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(green)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = statusText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = green
                    )
                }

                Spacer(Modifier.height(10.dp))

                Divider(color = border.copy(alpha = 0.7f), thickness = 1.dp)

                Spacer(Modifier.height(10.dp))

                InfoRow(label = "Category:", value = categoryText)
                InfoRow(label = "Date & Time:", value = dateTimeText)
                InfoRow(label = "Location:", value = locationText)

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Description:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite
                )
                Text(
                    text = descriptionText,
                    fontSize = 12.sp,
                    color = TextOnWhite,
                    lineHeight = 14.sp
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Attachment:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TextOnWhite
                    )
                    Spacer(Modifier.width(8.dp))
                    OutlinedButton(
                        onClick = onViewImage,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        shape = RoundedCornerShape(6.dp),
                        border = ButtonDefaults.outlinedButtonBorder.copy(
                            brush = androidx.compose.ui.graphics.SolidColor(border)
                        ),
                        modifier = Modifier.height(24.dp)
                    ) {
                        Text(
                            text = "View Image",
                            fontSize = 10.sp,
                            color = TextOnWhite,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                InfoRow(label = "Reported By:", value = reportedByText)

                Spacer(Modifier.height(10.dp))

                Divider(color = border.copy(alpha = 0.7f), thickness = 1.dp)

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "ACTIVITY LOG:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite
                )
                Spacer(Modifier.height(6.dp))

                activityLogs.forEach {
                    Text(
                        text = it,
                        fontSize = 12.sp,
                        color = TextOnWhite,
                        fontStyle = FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AdminNavBar(
                modifier = Modifier,
                navController
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
            color = TextOnWhite,
            modifier = Modifier.width(96.dp)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = TextOnWhite,
            lineHeight = 14.sp
        )
    }
}
