package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.model.AlertStatusOpt
import com.example.bantaycampus01.model.CampusRiskOpt
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.*
import com.example.bantaycampus01.screens.Admin.PopUps.*

@Composable
fun AdminAlertPage(
    modifier: Modifier,
    navController: NavController,

    adminName: String = "Admin",
    onHomeNav: () -> Unit = {},
    onAlertNav: () -> Unit = {},
    onIncomingNav: () -> Unit = {},
    onSafetyNav: () -> Unit = {},
    onProfileNav: () -> Unit = {}
) {
    val cardBg = Color(0xFFE6E6E6)
    val cardBorder = Color(0xFF6F7A8E)
    val pillColor = DarkGrayBlue

    val pageScroll = rememberScrollState()

    // dialog toggles
    var showDetails by remember { mutableStateOf(false) }
    var showDispatch by remember { mutableStateOf(false) }
    var showDispatchDone by remember { mutableStateOf(false) }
    var showTimeline by remember { mutableStateOf(false) }

    // dropdown states (kept here so dialogs can reuse)
    var statusExpanded by remember { mutableStateOf(false) }
    var campusExpanded by remember { mutableStateOf(false) }

    // main states
    var selectedStatus by remember { mutableStateOf(AlertStatusOpt.RESPONDING) }
    var selectedCampus by remember { mutableStateOf(CampusRiskOpt.HIGH) }
    var adminNote by remember { mutableStateOf("") }

    // dispatch states
    var guardA by remember { mutableStateOf(false) }
    var guardB by remember { mutableStateOf(false) }
    var guardC by remember { mutableStateOf(false) }
    var guardD by remember { mutableStateOf(false) }
    var guardE by remember { mutableStateOf(false) }
    var guardF by remember { mutableStateOf(false) }
    var medicalUnit by remember { mutableStateOf(false) }
    var dispatchNote by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .verticalScroll(pageScroll)
        ) {
            AdminHeader(adminName = adminName)

            Text(
                text = "ALERTS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite,
                modifier = Modifier.padding(horizontal = 30.dp, vertical = 14.dp)
            )

            AlertCard(
                modifier = Modifier
                    .padding(horizontal = 18.dp)
                    .fillMaxWidth(),
                cardBg = cardBg,
                borderColor = cardBorder,
                pillColor = pillColor,
                onViewDetails = { showDetails = true },
                onAcknowledge = { selectedStatus = AlertStatusOpt.ACK },
                onRespond = { showDispatch = true },
                onStatus = { showTimeline = true },
                statusLabel = selectedStatus.label
            )

            Spacer(modifier = Modifier.height(90.dp))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AdminNavBar(
                modifier = Modifier,
                navController
            )
        }

        // ✅ POPUPS (separated files)
        AdminAlertDetailsDialog(
            show = showDetails,

            statusExpanded = statusExpanded,
            onStatusExpandedChange = { statusExpanded = it },

            campusExpanded = campusExpanded,
            onCampusExpandedChange = { campusExpanded = it },

            selectedStatus = selectedStatus,
            onSelectedStatusChange = { selectedStatus = it },

            selectedCampus = selectedCampus,
            onSelectedCampusChange = { selectedCampus = it },

            adminNote = adminNote,
            onAdminNoteChange = { adminNote = it },

            onUpdate = { showDetails = false },
            onDismiss = { showDetails = false }
        )

        AdminDispatchDialog(
            show = showDispatch,

            guardA = guardA, onGuardAChange = { guardA = it },
            guardB = guardB, onGuardBChange = { guardB = it },
            guardC = guardC, onGuardCChange = { guardC = it },
            guardD = guardD, onGuardDChange = { guardD = it },
            guardE = guardE, onGuardEChange = { guardE = it },
            guardF = guardF, onGuardFChange = { guardF = it },
            medicalUnit = medicalUnit, onMedicalUnitChange = { medicalUnit = it },

            dispatchNote = dispatchNote,
            onDispatchNoteChange = { dispatchNote = it },

            onDispatch = {
                selectedStatus = AlertStatusOpt.RESPONDING
                showDispatch = false
                showDispatchDone = true
            },
            onDismiss = { showDispatch = false }
        )

        AdminDispatchDoneDialog(
            show = showDispatchDone,
            onDismiss = { showDispatchDone = false }
        )

        AdminStatusTimelineDialog(
            show = showTimeline,
            onDismiss = { showTimeline = false }
        )
    }
}

@Composable
private fun AlertCard(
    modifier: Modifier = Modifier,
    cardBg: Color,
    borderColor: Color,
    pillColor: Color,
    onViewDetails: () -> Unit,
    onAcknowledge: () -> Unit,
    onRespond: () -> Unit,
    onStatus: () -> Unit,
    statusLabel: String
) {
    Column(
        modifier = modifier
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .background(cardBg, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "🚨  Suspicious Activity",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextOnWhite
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "20 seconds ago",
                fontSize = 10.sp,
                fontStyle = FontStyle.Italic,
                color = TextOnWhite
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Student: Maria Santos (2023123456)",
            fontSize = 14.sp,
            color = TextOnWhite,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "📍 Near Library Entrance",
            fontSize = 14.sp,
            color = TextOnWhite
        )
        Text(
            text = "⏱ 1 minute ago",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Urgency: 🔴 High",
            fontSize = 14.sp,
            color = TextOnWhite
        )
        Text(
            text = "Status: 🟠 $statusLabel",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "View Details",
            fontSize = 14.sp,
            color = TextOnWhite,
            style = TextStyle(textDecoration = TextDecoration.Underline),
            modifier = Modifier.clickable { onViewDetails() }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            MiniPillButton(
                text = "ACKNOWLEDGE",
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
                text = "STATUS",
                container = pillColor,
                onClick = onStatus,
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