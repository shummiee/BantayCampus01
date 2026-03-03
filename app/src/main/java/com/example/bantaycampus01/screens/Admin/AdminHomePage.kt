package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.model.CampusRiskLevel
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.*
import com.example.bantaycampus01.screens.Admin.PopUps.*

@Composable
fun AdminHomePage(
    modifier: Modifier,
    navController: NavController,

    adminName: String = "Admin",
    dateText: String = "February 3, 2026",
    alertsCount: Int = 8,
    activeCases: Int = 1,
    avgResponse: String = "1 min. 15 secs.",
    studentCheckIn: Int = 415,
    campusRiskSafe: Boolean = true,
    lastUpdatedText: String = "Last Updated 10:45 am by Admin",
    safetyTitle: String = "SAFETY UPDATE",
    safetyMessage: String = "Power outage at Academic Building",
    safetyStatus: String = "RESOLVED",
    advisoryText: String = "Due to heavy rain, some walkways may be slippery. Please take extra caution when moving around campus.",

    onIncomingAlertsClick: () -> Unit = {},
    onManageCasesClick: () -> Unit = {},
    onRiskControlClick: () -> Unit = {}
) {
    val header = DarkGrayBlue
    val screenScroll = rememberScrollState()

    // Advisory state
    var advisory by remember { mutableStateOf(advisoryText) }
    var showAdvisoryDialog by remember { mutableStateOf(false) }
    var tempAdvisory by remember { mutableStateOf(advisoryText) }

    // Campus status state
    var showCampusStatusDialog by remember { mutableStateOf(false) }
    val statusOptions = listOf("RESOLVED", "CAUTION", "RESTRICTED")
    var selectedCampusStatus by remember { mutableStateOf(statusOptions[0]) }
    var tempCampusStatus by remember { mutableStateOf(selectedCampusStatus) }

    var campusNote by remember { mutableStateOf("") }
    var tempCampusNote by remember { mutableStateOf(campusNote) }
    var statusExpanded by remember { mutableStateOf(false) }

    var safetyStatusState by remember { mutableStateOf(safetyStatus) }
    var safetyMessageState by remember { mutableStateOf(safetyMessage) }

    val statusDotColor = when (safetyStatusState.uppercase()) {
        "SAFE" -> Color(0xFF29C65E)
        "CAUTION" -> Color(0xFFF4B400)
        "RESTRICTED" -> Color(0xFFE53935)
        "RESOLVED" -> Color(0xFF29C65E)
        else -> Color(0xFF29C65E)
    }

    // Risk control state
    var showRiskControlDialog by remember { mutableStateOf(false) }

    var currentRiskLevel by remember {
        mutableStateOf(if (campusRiskSafe) CampusRiskLevel.SAFE else CampusRiskLevel.HIGH)
    }

    var tempRiskLevel by remember { mutableStateOf(currentRiskLevel) }
    var riskNote by remember { mutableStateOf("") }
    var tempRiskNote by remember { mutableStateOf(riskNote) }
    var riskExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // keeps navbar visible
                .verticalScroll(screenScroll)
        ) {
            AdminHeader(adminName = adminName)

            Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp)) {
                Text(
                    text = "DASHBOARD",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite
                )
                Text(
                    text = dateText,
                    fontSize = 11.sp,
                    color = SubTextLabel
                )
            }

            Column(modifier = Modifier.padding(horizontal = 14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.8f)
                    ) {
                        MetricCard(
                            title = "NO. OF ALERTS",
                            bigText = alertsCount.toString()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.8f)
                    ) {
                        MetricCard(
                            title = "ACTIVE CASES",
                            bigText = activeCases.toString()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.8f)
                    ) {
                        MetricCard(
                            title = "AVERAGE\nRESPONSE TIME",
                            smallText = avgResponse
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1.8f)
                    ) {
                        MetricCard(
                            title = "STUDENT CHECK-IN",
                            bigText = studentCheckIn.toString()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(header)
                    .padding(horizontal = 14.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "CAMPUS RISK LEVEL",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnDark
                )

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Current Campus Status: ${currentRiskLevel.label}",
                        fontSize = 14.sp,
                        color = TextOnDark
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(currentRiskLevel.dotColor)
                    )
                }

                Text(
                    text = lastUpdatedText,
                    fontSize = 12.sp,
                    color = TextOnDark.copy(alpha = 0.75f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(White)
                        .padding(12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = safetyTitle,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnWhite
                        )

                        Spacer(modifier = Modifier.weight(0.1f))

                        Text(
                            text = "9:23 AM",
                            fontSize = 11.sp,
                            color = SubTextLabel
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = {
                                tempCampusStatus = selectedCampusStatus
                                tempCampusNote = campusNote
                                showCampusStatusDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = header),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "UPDATE",
                                fontSize = 14.sp,
                                color = TextOnDark,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Update",
                                tint = TextOnDark,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(PopUpButton)
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = safetyMessageState,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextOnDark,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Status: $safetyStatusState",
                                fontSize = 16.sp,
                                color = TextOnDark,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(statusDotColor)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionButton(
                        text = "INCOMING\nALERTS",
                        iconRes = R.drawable.incoming,
                        container = Color(0xFFB50000),
                        content = White,
                        onClick = { navController.navigate("AdminAlertPage_Screen") }
                    )

                    ActionButton(
                        text = "ALERT\nHISTORY",
                        iconRes = R.drawable.cases,
                        container = TextBoxBg,
                        content = TextOnWhite,
                        onClick = { navController.navigate("AdminAlertHistory_Screen") }
                    )

                    ActionButton(
                        text = "CAMPUS RISK\nCONTROL",
                        iconRes = R.drawable.risk,
                        container = Color(0xFFC8D6F2),
                        content = TextOnWhite,
                        onClick = {
                            tempRiskLevel = currentRiskLevel
                            tempRiskNote = riskNote
                            showRiskControlDialog = true
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(58.dp * 3 + 10.dp * 2)
                        .clip(RoundedCornerShape(16.dp))
                        .background(header)
                        .padding(12.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "CAMPUS\nADVISORY",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnDark,
                            lineHeight = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )

                        IconButton(
                            onClick = {
                                tempAdvisory = advisory
                                showAdvisoryDialog = true
                            },
                            modifier = Modifier
                                .size(28.dp)
                                .align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Edit",
                                tint = TextOnDark,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = advisory,
                        fontSize = 12.sp,
                        color = TextOnDark.copy(alpha = 0.9f),
                        lineHeight = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "\"Committed to your safety on campus.\"",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = TextOnWhite
            )

            Spacer(modifier = Modifier.height(90.dp))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AdminNavBar(
                modifier = Modifier,
                navController
            )
        }

        // ✅ POPUPS (now from separate files)
        AdminAdvisoryDialog(
            show = showAdvisoryDialog,
            tempAdvisory = tempAdvisory,
            onTempAdvisoryChange = { tempAdvisory = it },
            onUpdate = {
                advisory = tempAdvisory.trim()
                showAdvisoryDialog = false
            },
            onDismiss = { showAdvisoryDialog = false }
        )

        AdminCampusStatusDialog(
            show = showCampusStatusDialog,
            statusOptions = statusOptions,
            statusExpanded = statusExpanded,
            onStatusExpandedChange = { statusExpanded = it },
            tempCampusStatus = tempCampusStatus,
            onTempCampusStatusChange = { tempCampusStatus = it },
            tempCampusNote = tempCampusNote,
            onTempCampusNoteChange = { tempCampusNote = it },
            onUpdate = {
                selectedCampusStatus = tempCampusStatus
                campusNote = tempCampusNote

                safetyStatusState = tempCampusStatus
                if (campusNote.isNotBlank()) {
                    safetyMessageState = campusNote.trim()
                }
                showCampusStatusDialog = false
            },
            onDismiss = { showCampusStatusDialog = false }
        )

        AdminRiskControlDialog(
            show = showRiskControlDialog,
            riskExpanded = riskExpanded,
            onRiskExpandedChange = { riskExpanded = it },
            tempRiskLevel = tempRiskLevel,
            onTempRiskLevelChange = { tempRiskLevel = it },
            tempRiskNote = tempRiskNote,
            onTempRiskNoteChange = { tempRiskNote = it },
            onUpdate = {
                currentRiskLevel = tempRiskLevel
                riskNote = tempRiskNote
                showRiskControlDialog = false
                onRiskControlClick()
            },
            onDismiss = { showRiskControlDialog = false }
        )
    }
}

@Composable
private fun MetricCard(
    title: String,
    bigText: String? = null,
    smallText: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkGrayBlue)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = TextOnDark,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(6.dp))

        if (bigText != null) {
            Text(
                text = bigText,
                fontSize = 26.sp,
                fontWeight = FontWeight.Black,
                color = TextOnDark
            )
        } else if (smallText != null) {
            Text(
                text = smallText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextOnDark,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    iconRes: Int,
    container: Color,
    content: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp),
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        color = container,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = content,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}