package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.model.AlertCategory
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class FirestoreAlertItem(
    val id: String,
    val type: AlertCategory,
    val title: String,
    val timeRight: String,
    val location: String,
    val statusLabel: String,
    val statusColor: Color,
    val messagePreview: String,
    val rawDescription: String,
    val reportedBy: String,
    val categoryText: String,
    val dateTimeText: String,
    val createdAt: Long,
    val source: String,
    val activityLogs: List<String> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAlertScreen(
    modifier: Modifier,
    navController: NavController,
    userName: String = "User"
) {
    val border = Color(0xFF6F7A8E)
    val cardBg = Color(0xFFCAD6EE)
    val db = remember { FirebaseFirestore.getInstance() }

    var reportItems by remember { mutableStateOf(listOf<FirestoreAlertItem>()) }
    var sosItems by remember { mutableStateOf(listOf<FirestoreAlertItem>()) }

    var selectedCategory by remember { mutableStateOf(AlertCategory.ALL) }
    var categorySheetOpen by remember { mutableStateOf(false) }
    var filterSheetOpen by remember { mutableStateOf(false) }

    var tempCategory by remember { mutableStateOf(selectedCategory) }

    val filterOptions = listOf("Newest", "Oldest")
    var selectedSort by remember { mutableStateOf(filterOptions[0]) }
    var tempSort by remember { mutableStateOf(selectedSort) }

    DisposableEffect(Unit) {
        val reportsListener: ListenerRegistration =
            db.collection("reports")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener

                    reportItems = snapshot?.documents?.mapNotNull { doc ->
                        val incidentType = doc.getString("incidentType") ?: return@mapNotNull null
                        val location = doc.getString("location") ?: "Unknown location"
                        val status = doc.getString("status") ?: "PENDING"
                        val description = doc.getString("description") ?: "No description provided."
                        val createdAt = doc.getLong("createdAt") ?: 0L
                        val reporterName =
                            doc.getString("reportedBy")
                                ?: doc.getString("userName")
                                ?: doc.getString("name")
                                ?: "User"

                        FirestoreAlertItem(
                            id = doc.id,
                            type = mapIncidentTypeToCategory(incidentType),
                            title = incidentType,
                            timeRight = formatDateTime(createdAt),
                            location = location,
                            statusLabel = formatStatus(status),
                            statusColor = getStatusColor(status),
                            messagePreview = "\"${description.take(80)}${if (description.length > 80) "..." else ""}\"",
                            rawDescription = description,
                            reportedBy = reporterName,
                            categoryText = buildCategoryText(incidentType, false),
                            dateTimeText = formatDateTime(createdAt),
                            createdAt = createdAt,
                            source = "REPORT",
                            activityLogs = buildActivityLogs(
                                createdAt = createdAt,
                                status = status,
                                source = "REPORT"
                            )
                        )
                    } ?: emptyList()
                }

        val sosListener: ListenerRegistration =
            db.collection("sos_alerts")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener

                    sosItems = snapshot?.documents?.mapNotNull { doc ->
                        val location =
                            doc.getString("location")
                                ?: doc.getString("address")
                                ?: "Unknown location"

                        val status = doc.getString("status") ?: "CRITICAL"
                        val message =
                            doc.getString("message")
                                ?: doc.getString("description")
                                ?: "SOS alert triggered."
                        val createdAt = doc.getLong("createdAt") ?: 0L
                        val reporterName =
                            doc.getString("reportedBy")
                                ?: doc.getString("userName")
                                ?: doc.getString("name")
                                ?: "User"

                        FirestoreAlertItem(
                            id = doc.id,
                            type = AlertCategory.SECURITY,
                            title = "SOS Alert",
                            timeRight = formatDateTime(createdAt),
                            location = location,
                            statusLabel = formatStatus(status),
                            statusColor = getStatusColor(status),
                            messagePreview = "\"${message.take(80)}${if (message.length > 80) "..." else ""}\"",
                            rawDescription = message,
                            reportedBy = reporterName,
                            categoryText = "🆘 SOS Alert",
                            dateTimeText = formatDateTime(createdAt),
                            createdAt = createdAt,
                            source = "SOS",
                            activityLogs = buildActivityLogs(
                                createdAt = createdAt,
                                status = status,
                                source = "SOS"
                            )
                        )
                    } ?: emptyList()
                }

        onDispose {
            reportsListener.remove()
            sosListener.remove()
        }
    }

    val allItems = (reportItems + sosItems)

    val displayedItems = allItems
        .filter { selectedCategory == AlertCategory.ALL || it.type == selectedCategory }
        .let { list ->
            when (selectedSort) {
                "Oldest" -> list.sortedBy { it.createdAt }
                else -> list.sortedByDescending { it.createdAt }
            }
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                SmallPillButton(
                    text = "CATEGORY",
                    trailing = "▾",
                    onClick = {
                        tempCategory = selectedCategory
                        categorySheetOpen = true
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                SmallPillButton(
                    text = "FILTER",
                    trailing = "☰",
                    onClick = {
                        tempSort = selectedSort
                        filterSheetOpen = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (displayedItems.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, border, RoundedCornerShape(14.dp)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(cardBg)
                                    .padding(18.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No alerts found.",
                                    fontSize = 14.sp,
                                    color = UserUI.DarkBlue,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                } else {
                    items(displayedItems) { item ->
                        UserAlertCard(
                            item = item,
                            bg = cardBg,
                            border = border,
                            onViewDetails = {
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_id", item.id)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_report_id", "Report ID: ${item.id}")

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_status_text", item.statusLabel)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_category_text", item.categoryText)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_date_time_text", item.dateTimeText)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_location_text", item.location)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_description_text", item.rawDescription)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_reported_by_text", item.reportedBy)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_source_text", item.source)

                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("alert_activity_logs", ArrayList(item.activityLogs))

                                navController.navigate("UserAlertDetails_Screen")
                            }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(90.dp)) }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(
                modifier = Modifier,
                navController = navController
            )
        }
    }

    if (categorySheetOpen) {
        ModalBottomSheet(onDismissRequest = { categorySheetOpen = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Category", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(14.dp))

                AlertCategory.entries.forEach { c ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(c.label)
                        RadioButton(
                            selected = (tempCategory == c),
                            onClick = { tempCategory = c }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { tempCategory = AlertCategory.ALL }
                    ) {
                        Text("Reset")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedCategory = tempCategory
                            categorySheetOpen = false
                        }
                    ) {
                        Text("Apply")
                    }
                }

                Spacer(Modifier.height(22.dp))
            }
        }
    }

    if (filterSheetOpen) {
        ModalBottomSheet(onDismissRequest = { filterSheetOpen = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Filter", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(14.dp))

                Text("Sort", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                filterOptions.forEach { opt ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(opt)
                        RadioButton(
                            selected = (tempSort == opt),
                            onClick = { tempSort = opt }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { tempSort = filterOptions[0] }
                    ) {
                        Text("Reset")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedSort = tempSort
                            filterSheetOpen = false
                        }
                    ) {
                        Text("Apply")
                    }
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
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
        modifier = Modifier.height(28.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = UserUI.DarkBlue
        )
        Spacer(Modifier.width(6.dp))
        Text(trailing, fontSize = 12.sp, color = UserUI.DarkBlue)
    }
}

@Composable
private fun UserAlertCard(
    item: FirestoreAlertItem,
    bg: Color,
    border: Color,
    onViewDetails: () -> Unit
) {
    val icon = when (item.type) {
        AlertCategory.POWER -> "⚡"
        AlertCategory.MEDICAL -> "🩺"
        AlertCategory.SECURITY -> if (item.source == "SOS") "🆘" else "⚠️"
        AlertCategory.WEATHER -> "🌧️"
        AlertCategory.ALL -> "🔔"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(icon, fontSize = 16.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )
            }

            Text(
                text = item.timeRight,
                fontSize = 10.sp,
                color = UserUI.DarkBlue.copy(alpha = 0.75f),
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = "• ${item.location}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = UserUI.DarkBlue
        )

        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Status: ${item.statusLabel}",
                fontSize = 12.sp,
                color = UserUI.DarkBlue,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.width(6.dp))

            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(item.statusColor)
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = item.messagePreview,
            fontSize = 12.sp,
            color = UserUI.DarkBlue.copy(alpha = 0.9f)
        )

        Spacer(Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "View Details →",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = UserUI.DarkBlue.copy(alpha = 0.75f),
                modifier = Modifier.clickable { onViewDetails() }
            )
        }
    }
}

private fun mapIncidentTypeToCategory(incidentType: String): AlertCategory {
    return when (incidentType.trim().uppercase()) {
        "POWER OUTAGE", "POWER" -> AlertCategory.POWER
        "MEDICAL EMERGENCY", "MEDICAL" -> AlertCategory.MEDICAL
        "SUSPICIOUS ACTIVITY", "THEFT", "HARASSMENT", "SECURITY", "OTHER" -> AlertCategory.SECURITY
        "FLOOD", "HEAVY RAIN", "STORM", "WEATHER" -> AlertCategory.WEATHER
        else -> AlertCategory.SECURITY
    }
}

private fun getStatusColor(status: String): Color {
    return when (status.trim().uppercase()) {
        "PENDING" -> Color(0xFFF4B400)
        "RESPONDING" -> Color(0xFFFF9800)
        "ACKNOWLEDGED" -> Color(0xFFF4B400)
        "RESOLVED", "SAFE" -> UserUI.Green
        "RESTRICTED", "CRITICAL" -> Color(0xFFE53935)
        else -> Color.Gray
    }
}

private fun formatStatus(status: String): String {
    return status.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
}

private fun formatDateTime(timestamp: Long): String {
    if (timestamp <= 0L) return "Unknown time"
    return SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault()).format(Date(timestamp))
}

private fun buildCategoryText(title: String, isSos: Boolean): String {
    if (isSos) return "🆘 SOS Alert"

    return when (mapIncidentTypeToCategory(title)) {
        AlertCategory.POWER -> "⚡ $title"
        AlertCategory.MEDICAL -> "🩺 $title"
        AlertCategory.SECURITY -> "⚠️ $title"
        AlertCategory.WEATHER -> "🌧️ $title"
        AlertCategory.ALL -> "🔔 $title"
    }
}

private fun buildActivityLogs(
    createdAt: Long,
    status: String,
    source: String
): List<String> {
    val timeText = if (createdAt > 0L) {
        SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(createdAt))
    } else {
        "Unknown time"
    }

    val logs = mutableListOf<String>()
    logs.add("$timeText – ${if (source == "SOS") "SOS alert sent" else "Alert sent"}")

    when (status.trim().uppercase()) {
        "PENDING" -> logs.add("$timeText – Waiting for response")
        "RESPONDING" -> logs.add("$timeText – Security/admin is responding")
        "ACKNOWLEDGED" -> logs.add("$timeText – Acknowledged by admin")
        "RESOLVED", "SAFE" -> logs.add("$timeText – Incident resolved")
        "CRITICAL", "RESTRICTED" -> logs.add("$timeText – High-priority alert")
    }

    return logs
}