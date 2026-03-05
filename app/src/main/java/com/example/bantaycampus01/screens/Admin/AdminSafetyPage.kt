package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.*
import com.example.bantaycampus01.screens.Admin.PopUps.CAMPUS_STATUS_OPTIONS

// ✅ POPUPS: import from Admin.PopUps package
import com.example.bantaycampus01.screens.Admin.PopUps.*

@Composable
fun AdminSafetyPage(
    modifier: Modifier,
    navController: NavController,

    adminName: String = "Admin",

    onGoToCheckInPage: () -> Unit = {},
) {
    var showAdvisoryDialog by rememberSaveable { mutableStateOf(false) }
    var showCampusDialog by rememberSaveable { mutableStateOf(false) }

    var advisoryHeadline by rememberSaveable { mutableStateOf("Attention Students!") }
    var advisoryBody by rememberSaveable {
        mutableStateOf(
            "There is a power outage at the Academic Building that is currently being resolved.\nPlease take caution."
        )
    }
    var advisoryTime by rememberSaveable { mutableStateOf("10:45 AM • Ongoing") }
    var tempAdvisory by rememberSaveable { mutableStateOf(advisoryBody) }

    val statusOptions = CAMPUS_STATUS_OPTIONS

    var campusStatus by rememberSaveable { mutableStateOf("SAFE") }
    var campusMessage by rememberSaveable {
        mutableStateOf("\"Normal campus operations. No immediate threats reported.\"")
    }
    var campusTime by rememberSaveable { mutableStateOf("10:45 AM") }

    var statusExpanded by remember { mutableStateOf(false) }
    var tempCampusStatus by rememberSaveable { mutableStateOf(campusStatus) }
    var tempCampusNote by rememberSaveable { mutableStateOf(campusMessage.trim('"')) }

    AdminSafetyPageUI(
        adminName = adminName,

        advisoryHeadline = advisoryHeadline,
        advisoryBody = advisoryBody,
        advisoryTime = advisoryTime,

        campusUpdateStatus = campusStatus,
        campusUpdateMessage = campusMessage,
        campusUpdateTime = campusTime,

        onClockClick = onGoToCheckInPage,

        onUpdateAdvisory = {
            tempAdvisory = advisoryBody
            showAdvisoryDialog = true
        },

        onEditCampusUpdate = {
            tempCampusStatus = campusStatus
            tempCampusNote = campusMessage.trim('"')
            showCampusDialog = true
        },

        modifier = Modifier,
        navController = navController
    )

    // ✅ Advisory popup is now called from Admin.PopUps
    AdminAdvisoryDialog(
        show = showAdvisoryDialog,
        tempAdvisory = tempAdvisory,
        onTempAdvisoryChange = { tempAdvisory = it },
        onUpdate = {
            advisoryBody = tempAdvisory.trim()
            advisoryTime = "Now • Ongoing"
            showAdvisoryDialog = false
        },
        onDismiss = { showAdvisoryDialog = false }
    )

    // ✅ Campus Update popup is now called from Admin.PopUps
    AdminCampusStatusDialog(
        show = showCampusDialog,

        statusOptions = statusOptions,

        statusExpanded = statusExpanded,
        onStatusExpandedChange = { statusExpanded = it },

        tempCampusStatus = tempCampusStatus,
        onTempCampusStatusChange = { tempCampusStatus = it },

        tempCampusNote = tempCampusNote,
        onTempCampusNoteChange = { tempCampusNote = it },

        onUpdate = {
            campusStatus = tempCampusStatus
            campusMessage = "\"${tempCampusNote.trim()}\""
            campusTime = "Now"
            showCampusDialog = false
        },
        onDismiss = { showCampusDialog = false }
    )
}

@Composable
fun AdminSafetyPageUI(
    modifier: Modifier,
    navController: NavController,

    adminName: String = "Admin",

    currentStatusText: String = "Current Status: ALL SAFE",
    lastCheckinText: String = "Last StudentCheck-in 10 minutes ago",

    advisoryTitle: String = "Campus Security Advisory",
    advisoryTime: String = "10:45 AM • Ongoing",
    advisoryHeadline: String = "Attention Students!",
    advisoryBody: String =
        "There is a power outage at the Academic Building that is currently being resolved.\nPlease take caution.",

    campusUpdateTitle: String = "CAMPUS UPDATE",
    campusUpdateStatus: String = "SAFE",
    campusUpdateMessage: String =
        "\"Normal campus operations. No immediate threats reported.\"",
    campusUpdateTime: String = "10:45 AM",

    onClockClick: () -> Unit = {},
    onUpdateAdvisory: () -> Unit = {},
    onEditCampusUpdate: () -> Unit = {},
    onEditContacts: () -> Unit = {},
    onEditMaps: () -> Unit = {},

    onHomeNav: () -> Unit = {},
    onAlertNav: () -> Unit = {},
    onIncomingNav: () -> Unit = {},
    onSafetyNav: () -> Unit = {},
    onProfileNav: () -> Unit = {}
) {
    val header = DarkGrayBlue
    val advisoryBorder = Color(0xFFB50000)
    val cardBg = Color(0xFF2F3B55)
    val lightPanel2 = Color(0xFFC8D6F2)

    val statusDot = when (campusUpdateStatus.uppercase()) {
        "SAFE" -> Color(0xFF29C65E)
        "CAUTION" -> Color(0xFFF4B400)
        "RESTRICTED" -> Color(0xFFE53935)
        "RESOLVED" -> Color(0xFF29C65E)
        else -> Color(0xFF29C65E)
    }

    val screenScroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(screenScroll)
        ) {
            AdminHeader()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PopUpButton)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentStatusText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = White
                    )
                    Text(
                        text = lastCheckinText,
                        fontSize = 12.sp,
                        color = White.copy(alpha = 0.9f),
                        fontStyle = FontStyle.Italic
                    )
                }

                Surface(
                    color = White.copy(alpha = 0.25f),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .size(46.dp)
                        .clickable { onClockClick() }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🕒", fontSize = 24.sp)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .border(2.dp, advisoryBorder, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(White)
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "👤  $advisoryTitle",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = TextOnWhite
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = advisoryTime,
                        fontSize = 9.sp,
                        color = SubTextLabel
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = advisoryHeadline,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = advisoryBorder
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = advisoryBody,
                    fontSize = 12.sp,
                    color = TextOnWhite,
                    lineHeight = 14.sp
                )

                Spacer(Modifier.height(10.dp))

                Button(
                    onClick = onUpdateAdvisory,
                    modifier = Modifier
                        .align(Alignment.End)
                        .height(34.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = header),
                    shape = RoundedCornerShape(14.dp),
                    contentPadding = PaddingValues(horizontal = 18.dp)
                ) {
                    Text(
                        text = "UPDATE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = White
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = campusUpdateTitle,
                modifier = Modifier.padding(horizontal = 14.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite
            )

            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardBg)
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status: $campusUpdateStatus",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = White
                    )

                    Spacer(Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(statusDot)
                    )

                    Spacer(Modifier.weight(1f))

                    TextButton(
                        onClick = onEditCampusUpdate,
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text("✎", fontSize = 26.sp, color = White)
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = campusUpdateMessage,
                    fontSize = 14.sp,
                    color = White,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = campusUpdateTime,
                    fontSize = 12.sp,
                    color = White.copy(alpha = 0.85f),
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoPanel(
                    modifier = Modifier.weight(1f),
                    panelBg = header,
                    title = "EMERGENCY CONTACTS",
                    titleBg = DarkGrayBlue,
                    titleTextColor = White,
                    onEdit = onEditContacts
                ) {
                    MiniWhiteCard(title = "ADMIN", body = "+63 912 3456 789")
                    Spacer(Modifier.height(8.dp))
                    MiniWhiteCard(title = "GUARD", body = "+63 912 3456 789")
                    Spacer(Modifier.height(8.dp))
                    MiniWhiteCard(title = "CLINIC", body = "+63 912 3456 789")
                }

                InfoPanel(
                    modifier = Modifier.weight(1f),
                    panelBg = lightPanel2,
                    title = "FIRE EXIT MAPS",
                    titleBg = DarkGrayBlue,
                    titleTextColor = White,
                    onEdit = onEditMaps
                ) {
                    MiniWhiteCard(title = "ACADEMIC BUILDING", body = "2ND FLOOR")
                    Spacer(Modifier.height(8.dp))
                    MiniWhiteCard(title = "ACADEMIC BUILDING", body = "3RD FLOOR")
                    Spacer(Modifier.height(8.dp))
                    MiniWhiteCard(title = "ACADEMIC BUILDING", body = "4TH FLOOR")
                }
            }

            Spacer(Modifier.height(90.dp))
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
private fun InfoPanel(
    modifier: Modifier = Modifier,
    panelBg: Color,
    title: String,
    titleBg: Color,
    titleTextColor: Color,
    onEdit: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(panelBg)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = TextOnDark
            )
        }

        Spacer(Modifier.height(1.dp))
        content()
    }
}

@Composable
private fun MiniWhiteCard(
    title: String,
    body: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(White)
            .border(
                1.dp,
                DarkGrayBlue.copy(alpha = 0.45f),
                RoundedCornerShape(12.dp)
            )
            .padding(vertical = 10.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            lineHeight = 14.sp,
            color = TextOnWhite,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = body,
            fontSize = 12.sp,
            color = TextOnWhite,
            textAlign = TextAlign.Center
        )
    }
}