package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.*

private enum class AlertStatusOpt(val label: String, val dot: Color) {
    SENT("Sent", Color(0xFFF4B400)),
    ACK("Acknowledged", Color(0xFFF4B400)),
    RESPONDING("Responding", Color(0xFFF4B400)),
    RESOLVED("Resolved", Color(0xFF29C65E))
}

private enum class CampusRiskOpt(val label: String, val dot: Color) {
    SAFE("🟢 Safe", Color(0xFF29C65E)),
    MODERATE("🟡 Moderate", Color(0xFFF4B400)),
    HIGH("🟠 High", Color(0xFFFF8A00)),
    CRITICAL("🔴 Critical", Color(0xFFE53935))
}

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

    var showDetails by remember { mutableStateOf(false) }
    var showDispatch by remember { mutableStateOf(false) }
    var showDispatchDone by remember { mutableStateOf(false) }
    var showTimeline by remember { mutableStateOf(false) }

    var statusExpanded by remember { mutableStateOf(false) }
    var campusExpanded by remember { mutableStateOf(false) }

    var selectedStatus by remember { mutableStateOf(AlertStatusOpt.RESPONDING) }
    var selectedCampus by remember { mutableStateOf(CampusRiskOpt.HIGH) }
    var adminNote by remember { mutableStateOf("") }

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
                onAcknowledge = {
                    selectedStatus = AlertStatusOpt.ACK
                },
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
    }

    if (showDetails) {
        val dialogScroll = rememberScrollState()

        Dialog(onDismissRequest = { showDetails = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .heightIn(max = 620.dp),
                shape = RoundedCornerShape(16.dp),
                color = White,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(dialogScroll)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Alert ID: BC-2026-0045",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnDark,
                            modifier = Modifier
                                .background(Color(0xFF2E3B55), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { showDetails = false }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = TextOnWhite
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider(color = Color(0xFFB8B8B8))
                    Spacer(Modifier.height(10.dp))

                    DetailLine(label = "Category:", value = "🚨  Suspicious Activity")
                    DetailLine(label = "Reported By:", value = "Juan Dela Cruz\n(2023150518)")
                    DetailLine(label = "Time:", value = "Feb 3, 2026 – 9:23 AM")
                    DetailLine(label = "Location:", value = "📍 Near Library Entrance")
                    DetailLine(label = "Urgency:", value = "🔴 High")

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Description:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = TextOnWhite
                    )
                    Text(
                        text = "An unknown person is loitering near the library entrance and following students. The person is checking doors and acting suspiciously.",
                        fontSize = 12.sp,
                        color = TextOnWhite,
                        lineHeight = 15.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Attachment:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnWhite
                        )
                        Spacer(Modifier.width(8.dp))

                        OutlinedButton(
                            onClick = { /* TODO open image */ },
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text("View Image", fontSize = 11.sp)
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Status:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnWhite
                        )
                        Spacer(Modifier.width(10.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedButton(
                                onClick = { statusExpanded = true },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(34.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                            ) {
                                Text(
                                    text = selectedStatus.label,
                                    fontSize = 12.sp,
                                    color = TextOnWhite,
                                    modifier = Modifier.weight(1f)
                                )
                                Text("▾", color = TextOnWhite)
                            }

                            DropdownMenu(
                                expanded = statusExpanded,
                                onDismissRequest = { statusExpanded = false }
                            ) {
                                AlertStatusOpt.entries.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt.label, fontSize = 12.sp) },
                                        onClick = {
                                            selectedStatus = opt
                                            statusExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Current:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnWhite
                        )
                        Spacer(Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(selectedStatus.dot)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = selectedStatus.label,
                            fontSize = 12.sp,
                            color = TextOnWhite
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Campus Status:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnWhite
                        )
                        Spacer(Modifier.width(10.dp))

                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedButton(
                                onClick = { campusExpanded = true },
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(34.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp)
                            ) {
                                Text(
                                    text = selectedCampus.label,
                                    fontSize = 12.sp,
                                    color = TextOnWhite,
                                    modifier = Modifier.weight(1f)
                                )
                                Text("▾", color = TextOnWhite)
                            }

                            DropdownMenu(
                                expanded = campusExpanded,
                                onDismissRequest = { campusExpanded = false }
                            ) {
                                CampusRiskOpt.entries.forEach { opt ->
                                    DropdownMenuItem(
                                        text = { Text(opt.label, fontSize = 12.sp) },
                                        onClick = {
                                            selectedCampus = opt
                                            campusExpanded = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Note:",
                        fontSize = 12.sp,
                        color = TextOnWhite,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = adminNote,
                        onValueChange = { adminNote = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp),
                        placeholder = { Text("Enter note...", fontSize = 12.sp) },
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(Modifier.height(14.dp))

                    Button(
                        onClick = { showDetails = false },
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrayBlue),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(40.dp),
                        contentPadding = PaddingValues(horizontal = 26.dp)
                    ) {
                        Text(
                            text = "UPDATE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnDark
                        )
                    }
                }
            }
        }
    }

    if (showDispatch) {
        val dialogScroll = rememberScrollState()

        Dialog(onDismissRequest = { showDispatch = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .heightIn(max = 620.dp),
                shape = RoundedCornerShape(16.dp),
                color = White,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(dialogScroll)
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "Alert ID: BC-2026-0045",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnDark,
                            modifier = Modifier
                                .background(Color(0xFF2E3B55), RoundedCornerShape(6.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { showDispatch = false }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = TextOnWhite
                            )
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Dispatch Response Team",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextOnWhite
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = "Are you sure you want to\nmark this alert as\nResponding and assign\na security unit?",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = TextOnWhite,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        lineHeight = 18.sp
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Assign Response Team:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextOnWhite
                    )

                    Spacer(Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        SmallCheckRow("Guard A", guardA) { guardA = it }
                        SmallCheckRow("Guard B", guardB) { guardB = it }
                        SmallCheckRow("Guard C", guardC) { guardC = it }
                        SmallCheckRow("Guard D", guardD) { guardD = it }
                        SmallCheckRow("Guard E", guardE) { guardE = it }
                        SmallCheckRow("Guard F", guardF) { guardF = it }
                        SmallCheckRow("Medical Unit", medicalUnit) { medicalUnit = it }
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Note (optional)",
                        fontSize = 12.sp,
                        color = TextOnWhite,
                        fontStyle = FontStyle.Italic
                    )

                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = dispatchNote,
                        onValueChange = { dispatchNote = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp),
                        placeholder = { Text("Add dispatch note...", fontSize = 12.sp) },
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                selectedStatus = AlertStatusOpt.RESPONDING
                                showDispatch = false
                                showDispatchDone = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkGrayBlue),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "DISPATCH",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = TextOnDark
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = { showDispatch = false },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkGrayBlue),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
                        ) {
                            Text(
                                text = "CANCEL",
                                fontWeight = FontWeight.Black,
                                fontSize = 12.sp,
                                color = TextOnDark
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDispatchDone) {
        Dialog(onDismissRequest = { showDispatchDone = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                shape = RoundedCornerShape(16.dp),
                color = White,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF29C65E),
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Response Dispatched",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Black,
                                color = TextOnWhite
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { showDispatchDone = false }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = TextOnWhite
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Security personnel have\nbeen notified and are now\nresponding to this incident.",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = TextOnWhite,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }

    if (showTimeline) {
        Dialog(onDismissRequest = { showTimeline = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 28.dp),
                shape = RoundedCornerShape(16.dp),
                color = White,
                shadowElevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "STATUS TIMELINE",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = TextOnWhite
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { showTimeline = false }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = TextOnWhite
                            )
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    TimelineRow("9:23 AM", "Alert Sent")
                    HorizontalDivider()
                    TimelineRow("9:24 AM", "Acknowledged by Admin")
                    HorizontalDivider()
                    TimelineRow("9:26 AM", "Guard Dispatched")
                    HorizontalDivider()
                    TimelineRow("(Pending)", "Case Resolved")
                }
            }
        }
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
private fun DetailLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = TextOnWhite,
            modifier = Modifier.width(92.dp)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            color = TextOnWhite,
            lineHeight = 15.sp
        )
    }
    Spacer(Modifier.height(6.dp))
}

@Composable
private fun SmallCheckRow(
    text: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onChecked
        )
        Spacer(Modifier.width(2.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = TextOnWhite
        )
    }
}

@Composable
private fun TimelineRow(left: String, right: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = left,
            fontSize = 13.sp,
            color = TextOnWhite,
            modifier = Modifier.width(86.dp)
        )
        Text(
            text = "– $right",
            fontSize = 13.sp,
            color = TextOnWhite
        )
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
