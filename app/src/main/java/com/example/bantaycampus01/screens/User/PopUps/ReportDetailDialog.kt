package com.example.bantaycampus01.screens.User.Menu.PopUps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun ReportDetailDialog(
    show: Boolean,

    // top pill
    reportIdLabel: String = "Report ID: #BC-2026-0145",

    // status line
    statusLabel: String = "Responding",
    statusColor: Color = Color(0xFFF4B400), // orange

    // details
    category: String = "🚨 Suspicious Activity",
    dateTime: String = "Feb 3, 2026 – 9:23AM",
    location: String = "Near Library Entrance",
    description: String = "There is a person acting suspiciously near the stairs, checking doors and following students.",

    // attachment
    hasAttachment: Boolean = true,
    onViewAttachment: () -> Unit = {},

    // bottom action (backend/state update)
    onMarkSafe: () -> Unit = {},

    // ✅ NEW: tell parent to show thank-you dialog
    onShowMarkedSafe: () -> Unit = {},

    onDismiss: () -> Unit
) {
    if (!show) return

    val scroll = rememberScrollState()

    val modalShape = RoundedCornerShape(18.dp)
    val pillShape = RoundedCornerShape(6.dp)
    val fieldBorder = Color(0xFFB7B7B7)

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .heightIn(max = 620.dp),
            shape = modalShape,
            color = Color.White,
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scroll)
                    .padding(14.dp)
            ) {
                // Top row: pill + close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = reportIdLabel,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier
                            .background(UserUI.DarkBlue, pillShape)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = UserUI.DarkBlue
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))

                // Status line
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Status:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue
                    )

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        text = statusLabel,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = statusColor
                    )
                }

                Spacer(Modifier.height(10.dp))

                Divider(color = Color(0xFFB0B7C3), thickness = 1.dp)

                Spacer(Modifier.height(12.dp))

                // Details
                DetailRow(label = "Category:", value = category)
                DetailRow(label = "Date & Time:", value = dateTime)
                DetailRow(label = "Location:", value = location)

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Description:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = UserUI.DarkBlue,
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(10.dp))

                // Attachment row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Attachment:",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue
                    )

                    Spacer(Modifier.width(10.dp))

                    if (hasAttachment) {
                        Row(
                            modifier = Modifier
                                .height(28.dp)
                                .border(1.dp, fieldBorder, RoundedCornerShape(4.dp))
                                .background(Color(0xFFEDEDED), RoundedCornerShape(4.dp))
                                .clickable { onViewAttachment() }
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "View Image",
                                fontSize = 12.sp,
                                color = UserUI.DarkBlue
                            )
                            Spacer(Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Filled.Image,
                                contentDescription = "View Attachment",
                                tint = UserUI.DarkBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else {
                        Text(
                            text = "None",
                            fontSize = 13.sp,
                            color = UserUI.DarkBlue.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                // MARK SAFE button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onMarkSafe()        // backend/update state
                            onDismiss()         // close report dialog
                            onShowMarkedSafe()  // show thank-you dialog in parent
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = UserUI.DarkBlue),
                        shape = RoundedCornerShape(18.dp),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "MARK SAFE",
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.width(10.dp))

                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF29C65E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            color = UserUI.DarkBlue
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            color = UserUI.DarkBlue
        )
    }
}