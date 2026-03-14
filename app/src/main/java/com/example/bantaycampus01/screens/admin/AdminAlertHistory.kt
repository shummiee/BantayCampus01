package com.example.bantaycampus01.screens.admin

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun AdminAlertHistory(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val db = remember { FirebaseFirestore.getInstance() }

    val historyAlerts = remember { mutableStateListOf<AdminIncomingAlert>() }
    var reportsCache by remember { mutableStateOf<List<AdminIncomingAlert>>(emptyList()) }
    var sosCache by remember { mutableStateOf<List<AdminIncomingAlert>>(emptyList()) }

    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun mergeHistoryAlerts() {
        val combined = (reportsCache + sosCache)
            .filter { it.status.uppercase() == "RESOLVED" }
            .sortedByDescending { it.createdAt }

        historyAlerts.clear()
        historyAlerts.addAll(combined)
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
                    mergeHistoryAlerts()
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
                    mergeHistoryAlerts()
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
                text = "ALERT HISTORY",
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
            } else if (historyAlerts.isEmpty()) {
                Text(
                    text = "No resolved alerts found.",
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
                    items(historyAlerts, key = { it.collectionName + "_" + it.alertId }) { alert ->
                        HistoryAlertCard(alert = alert)
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

@Composable
private fun HistoryAlertCard(alert: AdminIncomingAlert) {
    val cardBg = Color(0xFFE6E6E6)
    val borderColor = Color(0xFF6F7A8E)

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

        Text(
            text = "Status: ${alert.status}",
            fontSize = 14.sp,
            color = Color(0xFF29C65E),
            fontWeight = FontWeight.Bold
        )
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