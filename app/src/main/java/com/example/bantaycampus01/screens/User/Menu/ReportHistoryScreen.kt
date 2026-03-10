package com.example.bantaycampus01.screens.User

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bantaycampus01.model.ReportModel
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.User.Menu.PopUps.ReportDetailDialog
import com.example.bantaycampus01.viewmodel.ReportViewModel

@Composable
fun ReportHistoryScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val reportViewModel: ReportViewModel = viewModel()
    val screenScroll = rememberScrollState()

    val reports = remember { mutableStateListOf<ReportModel>() }

    var selectedReport by remember { mutableStateOf<ReportModel?>(null) }
    var showDetails by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        reportViewModel.fetchUserReports(
            onSuccess = { fetchedReports ->
                reports.clear()
                reports.addAll(fetchedReports)
            },
            onFailure = {
                Toast.makeText(
                    context,
                    "Failed to load reports: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
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
                .verticalScroll(screenScroll)
        ) {
            UserHeader()

            Text(
                text = "REPORT HISTORY",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )

            if (reports.isEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No reports found yet.",
                            color = UserUI.DarkBlue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    reports.forEach { report ->
                        HistoryItem(
                            timeAgo = reportViewModel.getTimeAgo(report.createdAt),
                            title = "🚨 ${report.incidentType}",
                            location = "📍 ${report.location}",
                            status = reportViewModel.getReadableStatus(report.status),
                            onClick = {
                                selectedReport = report
                                showDetails = true
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(90.dp))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(modifier = Modifier, navController = navController)
        }

        ReportDetailDialog(
            show = showDetails,
            reportIdLabel = "Report ID: ${selectedReport?.reportId ?: "N/A"}",
            statusLabel = selectedReport?.status ?: "Unknown",
            statusColor = when ((selectedReport?.status ?: "").uppercase()) {
                "PENDING" -> Color(0xFFF4B400)
                "RESPONDING" -> Color(0xFFFF9800)
                "RESOLVED" -> Color(0xFF29C65E)
                else -> Color.Gray
            },
            category = "🚨 ${selectedReport?.incidentType ?: "N/A"}",
            dateTime = selectedReport?.createdAt?.let {
                java.text.SimpleDateFormat("MMM d, yyyy - h:mm a", java.util.Locale.getDefault()).format(java.util.Date(it))
            } ?: "N/A",
            location = selectedReport?.location ?: "N/A",
            description = selectedReport?.description ?: "No description provided.",
            hasAttachment = false,
            onViewAttachment = { },
            onMarkSafe = {
                // optional later
            },
            onShowMarkedSafe = { },
            onDismiss = {
                showDetails = false
                selectedReport = null
            }
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = timeAgo,
                fontSize = 12.sp,
                color = UserUI.DarkBlue.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = UserUI.DarkBlue
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = location,
                fontSize = 14.sp,
                color = UserUI.DarkBlue
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = status,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = UserUI.DarkBlue
            )
        }
    }
}