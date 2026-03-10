package com.example.bantaycampus01.screens.User

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.User.Menu.PopUps.MarkSafeDialog   // ✅ ADD
import com.example.bantaycampus01.screens.User.Menu.PopUps.ReportDetailDialog
import com.example.bantaycampus01.screens.User.Menu.PopUps.ReportSentDialog
import com.example.bantaycampus01.screens.User.Menu.PopUps.SendReportDialog
import com.example.bantaycampus01.screens.User.Menu.PopUps.UrgencyLevel
import com.example.bantaycampus01.ui.theme.TextOnDark
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bantaycampus01.viewmodel.ReportViewModel

@Composable
fun UserHomePage(
    modifier: Modifier,
    navController: NavController,

    campusStatusText: String = "SAFE",
    lastUpdatedText: String = "Last Updated 10:45 am by Admin",
    safetyTitle: String = "SAFETY UPDATE",
    safetyTime: String = "9:23 AM",
    safetyMessage: String = "Power outage at Academic Building",
    safetyStatus: String = "RESOLVED",
    advisoryText: String = "Due to heavy rain, some walkways may be slippery. Please take extra caution when moving around campus.",

    onSendReportClick: () -> Unit = {},
    onReportStatusClick: () -> Unit = {},
) {

    val screenScroll = rememberScrollState()
    val context = LocalContext.current

    var showSendReport by rememberSaveable { mutableStateOf(false) }
    var showReportSent by rememberSaveable { mutableStateOf(false) }
    var showReportStatus by rememberSaveable { mutableStateOf(false) }

    // ✅ NEW: Success dialog after MARK SAFE
    var showMarkedSafeDialog by rememberSaveable { mutableStateOf(false) }

    var incidentExpanded by rememberSaveable { mutableStateOf(false) }

    val incidentOptions = listOf(
        "Suspicious Activity",
        "Medical Emergency",
        "Theft",
        "Harassment",
        "Other"
    )

    var selectedIncident by rememberSaveable { mutableStateOf("") }
    var reportLocation by rememberSaveable { mutableStateOf("") }
    var reportDescription by rememberSaveable { mutableStateOf("") }
    var urgency by rememberSaveable { mutableStateOf(UrgencyLevel.MODERATE) }

    val statusDotColor = when (campusStatusText.uppercase()) {
        "SAFE", "RESOLVED" -> UserUI.Green
        "CAUTION" -> Color(0xFFF4B400)
        "RESTRICTED" -> Color(0xFFE53935)
        else -> UserUI.Green
    }

    val reportViewModel: ReportViewModel = viewModel()

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        navController.navigate("UserNotification_Screen")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = UserUI.DarkBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UserUI.CardBg)
                    .padding(horizontal = 18.dp, vertical = 14.dp)
            ) {

                Text(
                    text = "CAMPUS RISK LEVEL",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnDark
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Current Campus Status: $campusStatusText",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(statusDotColor)
                    )
                }

                Text(
                    text = lastUpdatedText,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(modifier = Modifier.padding(12.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(modifier = Modifier.weight(1f)) {

                                Text(
                                    text = safetyTitle,
                                    fontWeight = FontWeight.Black,
                                    color = UserUI.DarkBlue,
                                    fontSize = 20.sp
                                )

                                Text(
                                    text = safetyTime,
                                    color = UserUI.DarkBlue.copy(alpha = 0.75f),
                                    fontSize = 11.sp
                                )
                            }

                            Surface(
                                onClick = { showMarkedSafeDialog = true },
                                shape = RoundedCornerShape(22.dp),
                                color = UserUI.DarkBlue
                            ) {

                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "MARK SAFE",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(UserUI.Green),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Text(
                                            text = "✓",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(UserUI.LightCard)
                                .padding(12.dp)
                        ) {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Text(
                                    text = safetyMessage,
                                    fontWeight = FontWeight.Black,
                                    color = UserUI.DarkBlue,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Text(
                                        text = "Status: $safetyStatus",
                                        fontWeight = FontWeight.Bold,
                                        color = UserUI.DarkBlue,
                                        fontSize = 16.sp
                                    )

                                    Spacer(modifier = Modifier.width(6.dp))

                                    Box(
                                        modifier = Modifier
                                            .size(9.dp)
                                            .clip(CircleShape)
                                            .background(
                                                when (safetyStatus.uppercase()) {
                                                    "RESOLVED", "SAFE" -> UserUI.Green
                                                    "CAUTION" -> Color(0xFFF4B400)
                                                    "RESTRICTED" -> Color(0xFFE53935)
                                                    else -> UserUI.Green
                                                }
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    ActionButton(
                        title = "SEND REPORT",
                        bg = UserUI.DangerRed,
                        fg = Color.White,
                        icon = R.drawable.incoming,
                        onClick = { showSendReport = true }
                    )

                    ActionButton(
                        title = "REPORT\nHISTORY",
                        bg = UserUI.PaleBlueCard,
                        fg = UserUI.DarkBlue,
                        icon = R.drawable.cases,
                        onClick = {
                            navController.navigate("UserReportHistory_Screen")
                        }
                    )

                    ActionButton(
                        title = "REPORT\nSTATUS",
                        bg = UserUI.PaleBlueCard,
                        fg = UserUI.DarkBlue,
                        icon = R.drawable.risk,
                        onClick = { showReportStatus = true }
                    )
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = UserUI.CardBg)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "CAMPUS\nADVISORY",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White)
                                .padding(10.dp)
                        ) {

                            Text(
                                text = advisoryText,
                                color = UserUI.DarkBlue,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "“Committed to your safety on campus.”",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center,
                color = UserUI.DarkBlue.copy(alpha = 0.65f),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(90.dp))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(modifier = Modifier, navController = navController)
        }

        SendReportDialog(
            show = showSendReport,
            incidentExpanded = incidentExpanded,
            onIncidentExpandedChange = { incidentExpanded = it },
            incidentOptions = incidentOptions,
            selectedIncident = selectedIncident,
            onSelectedIncidentChange = { selectedIncident = it },
            location = reportLocation,
            onLocationChange = { reportLocation = it },
            description = reportDescription,
            onDescriptionChange = { reportDescription = it },
            urgency = urgency,
            onUrgencyChange = { urgency = it },
            onUploadClick = { },
            onSubmit = {

                reportViewModel.submitReport(
                    incidentType = selectedIncident,
                    location = reportLocation,
                    description = reportDescription,
                    urgency = urgency.label,

                    onSuccess = {

                        showSendReport = false
                        showReportSent = true

                    },

                    onFailure = {

                        Toast.makeText(
                            context,
                            "Failed to send report",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                )
            },
            onDismiss = { showSendReport = false }
        )

        ReportSentDialog(
            show = showReportSent,
            onDismiss = { showReportSent = false }
        )

        // ✅ Report Status popup
        ReportDetailDialog(
            show = showReportStatus,
            onMarkSafe = {
                onReportStatusClick()
            },
            onShowMarkedSafe = { showMarkedSafeDialog = true }, // ✅ SHOW THANK-YOU
            onDismiss = { showReportStatus = false }             // ✅ CLOSE REPORT DETAIL
        )

        // ✅ Thank-you popup (shows after MARK SAFE)
        MarkSafeDialog(
            show = showMarkedSafeDialog,
            onConfirm = { showMarkedSafeDialog = false }, // CLOSE
            onDismiss = { showMarkedSafeDialog = false }
        )
    }
}

@Composable
private fun ActionButton(
    title: String,
    bg: Color,
    fg: Color,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = fg,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}