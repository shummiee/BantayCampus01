package com.example.bantaycampus01.screens.Admin.PopUps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.ui.theme.*

@Composable
fun AdminStatusTimelineDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onCaseSolved: () -> Unit = {}
) {
    if (!show) return

    var showConfirmDialog by remember { mutableStateOf(false) }

    AdminCaseSolveConfirmDialog(
        show = showConfirmDialog,
        onDismiss = { showConfirmDialog = false },
        onConfirm = {
            showConfirmDialog = false
            onCaseSolved()
            onDismiss()
        }
    )

    Dialog(onDismissRequest = onDismiss) {
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

                    IconButton(onClick = onDismiss) {
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

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(42.dp)
                        .widthIn(min = 160.dp),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TextOnWhite
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text = "CASE SOLVE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.height(6.dp))
            }
        }
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