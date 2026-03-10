package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.model.ReportModel
import com.example.bantaycampus01.model.SosAlert
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.TextOnWhite
import com.example.bantaycampus01.ui.theme.White
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

data class AdminIncomingAlert(
    val alertId: String = "",
    val source: String = "", // REPORT or SOS
    val title: String = "",
    val userName: String = "",
    val idNumber: String = "",
    val location: String = "",
    val description: String = "",
    val urgency: String = "",
    val status: String = "PENDING",
    val createdAt: Long = 0L,
    val rawTimeText: String = "",
    val collectionName: String = ""
)

@Composable
fun AdminAlertPage(
    modifier: Modifier,
    navController: NavController,
    adminName: String = "Admin"
) {
    val db = remember { FirebaseFirestore.getInstance() }

    val alerts = remember { mutableStateListOf<AdminIncomingAlert>() }
    var reportsCache by remember { mutableStateOf<List<AdminIncomingAlert>>(emptyList()) }
    var sosCache by remember { mutableStateOf<List<AdminIncomingAlert>>(emptyList()) }

    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun mergeAlerts() {
        val combined = (reportsCache + sosCache)
            .filter { it.status.uppercase() != "RESOLVED" }
            .sortedByDescending { it.createdAt }

        alerts.clear()
        alerts.addAll(combined)
    }

    DisposableEffect(Unit) {
        isLoading = true
        errorMessage = null

        var reportLoaded = false
        var sosLoaded = false

        fun tryStopLoading() {
            if (reportLoaded && sosLoaded) {
                isLoading = false
            }
        }

        val reportListener: ListenerRegistration =
            db.collection("reports")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        errorMessage = "Failed to read reports: ${error.message}"
                        reportLoaded = true
                        tryStopLoading()
                        return@addSnapshotListener
                    }

                    reportsCache = snapshot?.documents?.mapNotNull { doc ->
                        val report = doc.toObject(ReportModel::class.java) ?: return@mapNotNull null

                        AdminIncomingAlert(
                            alertId = if (report.reportId.isNotBlank()) report.reportId else doc.id,
                            source = "REPORT",
                            title = if (report.incidentType.isNotBlank()) report.incidentType else "User Report",
                            userName = if (report.userName.isNotBlank()) report.userName else "Unknown User",
                            idNumber = if (report.idNumber.isNotBlank()) report.idNumber else "No ID number",
                            location = if (report.location.isNotBlank()) report.location else "No location provided",
                            description = if (report.description.isNotBlank()) report.description else "No description provided",
                            urgency = if (report.urgency.isNotBlank()) report.urgency else "Not specified",
                            status = if (report.status.isNotBlank()) report.status else "PENDING",
                            createdAt = report.createdAt,
                            rawTimeText = "",
                            collectionName = "reports"
                        )
                    } ?: emptyList()

                    reportLoaded = true
                    mergeAlerts()
                    tryStopLoading()
                }

        val sosListener: ListenerRegistration =
            db.collection("sos_alerts")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        errorMessage = if (errorMessage.isNullOrBlank()) {
                            "Failed to read SOS alerts: ${error.message}"
                        } else {
                            errorMessage
                        }
                        sosLoaded = true
                        tryStopLoading()
                        return@addSnapshotListener
                    }

                    sosCache = snapshot?.documents?.mapNotNull { doc ->
                        val sos = doc.toObject(SosAlert::class.java) ?: return@mapNotNull null

                        AdminIncomingAlert(
                            alertId = if (sos.reportId.isNotBlank()) sos.reportId else doc.id,
                            source = "SOS",
                            title = "SOS Emergency",
                            userName = if (sos.userName.isNotBlank()) sos.userName else "Unknown User",
                            idNumber = if (sos.idNumber.isNotBlank()) sos.idNumber else "No ID number",
                            location = "Location not provided",
                            description = if (sos.message.isNotBlank()) sos.message else "SOS Emergency",
                            urgency = "HIGH",
                            status = if (sos.status.isNotBlank()) sos.status else "PENDING",
                            createdAt = System.currentTimeMillis(),
                            rawTimeText = sos.timestamp,
                            collectionName = "sos_alerts"
                        )
                    } ?: emptyList()

                    sosLoaded = true
                    mergeAlerts()
                    tryStopLoading()
                }

        onDispose {
            reportListener.remove()
            sosListener.remove()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
        ) {
            AdminHeader()

            Text(
                text = "ALERTS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 14.dp)
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = DarkGrayBlue)
                }
            } else if (!errorMessage.isNullOrBlank()) {
                Text(
                    text = errorMessage ?: "Unknown error",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
            } else if (alerts.isEmpty()) {
                Text(
                    text = "No incoming alerts found.",
                    fontSize = 15.sp,
                    color = TextOnWhite,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(alerts, key = { it.collectionName + "_" + it.alertId }) { alert ->
                        AlertCard(
                            alert = alert,
                            onAcknowledge = {
                                updateAlertStatus(
                                    db = db,
                                    collectionName = alert.collectionName,
                                    alertId = alert.alertId,
                                    newStatus = "ACKNOWLEDGED"
                                )
                            },
                            onRespond = {
                                updateAlertStatus(
                                    db = db,
                                    collectionName = alert.collectionName,
                                    alertId = alert.alertId,
                                    newStatus = "RESPONDING"
                                )
                            },
                            onResolve = {
                                updateAlertStatus(
                                    db = db,
                                    collectionName = alert.collectionName,
                                    alertId = alert.alertId,
                                    newStatus = "RESOLVED"
                                )
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(90.dp))
                    }
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AdminNavBar(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}

private fun updateAlertStatus(
    db: FirebaseFirestore,
    collectionName: String,
    alertId: String,
    newStatus: String
) {
    db.collection(collectionName)
        .document(alertId)
        .update("status", newStatus)
}

@Composable
private fun AlertCard(
    alert: AdminIncomingAlert,
    onAcknowledge: () -> Unit,
    onRespond: () -> Unit,
    onResolve: () -> Unit
) {
    val cardBg = Color(0xFFE6E6E6)
    val borderColor = Color(0xFF6F7A8E)
    val pillColor = DarkGrayBlue

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .background(cardBg, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = if (alert.source == "SOS") "🚨 SOS Emergency" else "📢 ${alert.title}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextOnWhite
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = if (alert.source == "SOS" && alert.rawTimeText.isNotBlank()) {
                    alert.rawTimeText
                } else {
                    getTimeAgo(alert.createdAt)
                },
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = TextOnWhite
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "User: ${alert.userName}",
            fontSize = 14.sp,
            color = TextOnWhite,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "ID Number: ${alert.idNumber}",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Location: ${alert.location}",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Text(
            text = "Description: ${alert.description}",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Urgency: ${alert.urgency}",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Status: ${alert.status}",
                fontSize = 14.sp,
                color = TextOnWhite
            )

            Spacer(modifier = Modifier.width(6.dp))

            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = statusColor(alert.status),
                        shape = CircleShape
                    )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiniPillButton(
                text = "ACK",
                container = pillColor,
                onClick = onAcknowledge,
                modifier = Modifier.weight(1f)
            )

            MiniPillButton(
                text = "RESPOND",
                container = pillColor,
                onClick = onRespond,
                modifier = Modifier.weight(1f)
            )

            MiniPillButton(
                text = "RESOLVE",
                container = pillColor,
                onClick = onResolve,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MiniPillButton(
    text: String,
    container: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(32.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = container),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = White,
            maxLines = 1
        )
    }
}

private fun statusColor(status: String): Color {
    return when (status.uppercase()) {
        "PENDING" -> Color(0xFFE53935)
        "ACKNOWLEDGED" -> Color(0xFFFF9800)
        "ACK" -> Color(0xFFFF9800)
        "RESPONDING" -> Color(0xFFFFC107)
        "RESOLVED" -> Color(0xFF29C65E)
        else -> Color(0xFF9E9E9E)
    }
}

private fun getTimeAgo(createdAt: Long): String {
    if (createdAt <= 0L) return "Unknown time"

    val now = System.currentTimeMillis()
    val diff = now - createdAt

    val minute = 60 * 1000L
    val hour = 60 * minute
    val day = 24 * hour

    return when {
        diff < minute -> "Just now"
        diff < hour -> "${diff / minute} min ago"
        diff < day -> "${diff / hour} hr ago"
        else -> "${diff / day} day(s) ago"
    }
}