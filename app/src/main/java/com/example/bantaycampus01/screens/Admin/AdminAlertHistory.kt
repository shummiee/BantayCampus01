package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.*

enum class AlertCategory(val label: String) {
    ALL("All Categories"),
    SUSPICIOUS("🚨 Suspicious Activity"),
    MEDICAL("🏥 Medical Emergency"),
    FIRE("🔥 Fire / Hazard"),
    POWER("⚡ Power / Facility Issue"),
    HARASSMENT("🧍 Harassment / Personal Threat"),
    OTHERS("❓ Others")
}

enum class AlertStatus(val label: String) {
    ALL("All Status"),
    SENT("Sent"),
    ACK("Acknowledged"),
    RESPONDING("Responding"),
    RESOLVED("Resolved")
}

enum class DateRange(val label: String) {
    TODAY("Today"),
    LAST_7("Last 7 days"),
    LAST_30("Last 30 days"),
    CUSTOM("Custom range")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAlertHistory(
    modifier: Modifier,
    navController: NavController,

    adminName: String = "Admin",
    onViewDetails1: () -> Unit = {},
    onViewDetails2: () -> Unit = {},
    onHomeNav: () -> Unit = {},
    onAlertNav: () -> Unit = {},
    onIncomingNav: () -> Unit = {},
    onSafetyNav: () -> Unit = {},
    onProfileNav: () -> Unit = {}
) {
    val border = Color(0xFF6F7A8E)
    val cardBg = Color(0xFFE3ECFF)
    val dotGreen = Color(0xFF29C65E)
    val dotYellow = Color(0xFFF2C94C)

    var selectedCategory by remember { mutableStateOf(AlertCategory.ALL) }
    var selectedStatus by remember { mutableStateOf(AlertStatus.ALL) }
    var selectedRange by remember { mutableStateOf(DateRange.TODAY) }

    var categoryExpanded by remember { mutableStateOf(false) }
    var filterOpen by remember { mutableStateOf(false) }

    var tempStatus by remember { mutableStateOf(selectedStatus) }
    var tempRange by remember { mutableStateOf(selectedRange) }

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ALERT HISTORY",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite
                )

                Spacer(modifier = Modifier.weight(1f))

                Box {
                    SmallPillButton(
                        text = "CATEGORY",
                        trailing = "▾",
                        border = DarkGrayBlue,
                        onClick = { categoryExpanded = true }
                    )

                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        AlertCategory.entries.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat.label, fontSize = 13.sp) },
                                onClick = {
                                    selectedCategory = cat
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                SmallPillButton(
                    text = "FILTER",
                    trailing = "☰",
                    border = DarkGrayBlue,
                    onClick = {
                        tempStatus = selectedStatus
                        tempRange = selectedRange
                        filterOpen = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AlertHistoryCard(
                    titleIcon = "⚡",
                    title = "Power Outage",
                    dateText = "Jan 27, 2026 • 8:10 AM",
                    location = "Academic Building • 2nd Floor",
                    statusText = "Status: Acknowledged",
                    statusDot = dotYellow,
                    details = "\"Lights are out in several classrooms...\"",
                    cardBg = cardBg,
                    border = border,
                    onViewDetails = onViewDetails1
                )

                AlertHistoryCard(
                    titleIcon = "🩺",
                    title = "Medical Emergency",
                    dateText = "Jan 27, 2026 • 8:10 AM",
                    location = "Canteen Area",
                    statusText = "Status: Resolved",
                    statusDot = dotGreen,
                    details = "\"Student fainted and needs medical assistance...\"",
                    cardBg = cardBg,
                    border = border,
                    onViewDetails = onViewDetails2
                )
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

    if (filterOpen) {
        ModalBottomSheet(
            onDismissRequest = { filterOpen = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Filter", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(14.dp))

                Text("Status", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                AlertStatus.entries.forEach { s ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(s.label)
                        RadioButton(
                            selected = (tempStatus == s),
                            onClick = { tempStatus = s }
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))
                Divider()
                Spacer(Modifier.height(10.dp))

                Text("Date / Time", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                DateRange.entries.forEach { r ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(r.label)
                        RadioButton(
                            selected = (tempRange == r),
                            onClick = { tempRange = r }
                        )
                    }
                }

                if (tempRange == DateRange.CUSTOM) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Custom range date picker can be added next.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            tempStatus = AlertStatus.ALL
                            tempRange = DateRange.TODAY
                        }
                    ) { Text("Reset") }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedStatus = tempStatus
                            selectedRange = tempRange
                            filterOpen = false
                        }
                    ) { Text("Apply") }
                }

                Spacer(Modifier.height(22.dp))
            }
        }
    }
}

@Composable
private fun SmallPillButton(
    text: String,
    trailing: String,
    onClick: () -> Unit,
    border: Color
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
        modifier = Modifier.height(26.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextOnWhite)
        Spacer(Modifier.width(6.dp))
        Text(trailing, fontSize = 12.sp, color = TextOnWhite)
    }
}


@Composable
private fun AlertHistoryCard(
    titleIcon: String,
    title: String,
    dateText: String,
    location: String,
    statusText: String,
    statusDot: Color,
    details: String,
    cardBg: Color,
    border: Color,
    onViewDetails: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, RoundedCornerShape(14.dp))
            .background(cardBg, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "$titleIcon  $title",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = dateText,
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = TextOnWhite
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "📍 $location",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = statusText,
                fontSize = 14.sp,
                color = TextOnWhite
            )
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(statusDot)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = details,
            fontSize = 12.sp,
            color = TextOnWhite,
            fontStyle = FontStyle.Italic
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = onViewDetails) {
                Text(
                    text = "View Details →",
                    fontSize = 12.sp,
                    color = TextOnWhite,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}
