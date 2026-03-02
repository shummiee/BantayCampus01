package com.example.bantaycampus01.screens.Admin.PopUps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.ui.theme.*
import com.example.bantaycampus01.model.*

@Composable
fun AdminAlertDetailsDialog(
    show: Boolean,

    statusExpanded: Boolean,
    onStatusExpandedChange: (Boolean) -> Unit,

    campusExpanded: Boolean,
    onCampusExpandedChange: (Boolean) -> Unit,

    selectedStatus: AlertStatusOpt,
    onSelectedStatusChange: (AlertStatusOpt) -> Unit,

    selectedCampus: CampusRiskOpt,
    onSelectedCampusChange: (CampusRiskOpt) -> Unit,

    adminNote: String,
    onAdminNoteChange: (String) -> Unit,

    onUpdate: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    val dialogScroll = rememberScrollState()

    Dialog(onDismissRequest = onDismiss) {
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

                    IconButton(onClick = onDismiss) {
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
                        onClick = { /* TODO: open image */ },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text("View Image", fontSize = 11.sp)
                    }
                }

                Spacer(Modifier.height(14.dp))

                // STATUS dropdown
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
                            onClick = { onStatusExpandedChange(true) },
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
                            onDismissRequest = { onStatusExpandedChange(false) }
                        ) {
                            AlertStatusOpt.entries.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt.label, fontSize = 12.sp) },
                                    onClick = {
                                        onSelectedStatusChange(opt)
                                        onStatusExpandedChange(false)
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

                // CAMPUS dropdown
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
                            onClick = { onCampusExpandedChange(true) },
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
                            onDismissRequest = { onCampusExpandedChange(false) }
                        ) {
                            CampusRiskOpt.entries.forEach { opt ->
                                DropdownMenuItem(
                                    text = { Text(opt.label, fontSize = 12.sp) },
                                    onClick = {
                                        onSelectedCampusChange(opt)
                                        onCampusExpandedChange(false)
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
                    onValueChange = onAdminNoteChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    placeholder = { Text("Enter note...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = onUpdate,
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