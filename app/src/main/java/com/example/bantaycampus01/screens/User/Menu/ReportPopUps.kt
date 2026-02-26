package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.*

@Composable
fun SendReportDialog(
    onClose: () -> Unit,
    onSend: () -> Unit
) {
    var incident by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {},
        title = null,
        text = {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.SoftGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Send Report", fontWeight = FontWeight.Black, color = UserUI.DarkBlue, fontSize = 18.sp)
                        Spacer(Modifier.weight(1f))
                        Text("✕", modifier = Modifier.padding(6.dp), color = UserUI.DarkBlue)
                    }

                    Spacer(Modifier.height(10.dp))

                    Text("Type of Incident", fontWeight = FontWeight.Bold, color = UserUI.DarkBlue)
                    OutlinedTextField(
                        value = incident,
                        onValueChange = { incident = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Fire, Suspicious Person") }
                    )

                    Spacer(Modifier.height(10.dp))

                    Text("Location", fontWeight = FontWeight.Bold, color = UserUI.DarkBlue)
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("e.g. Academic Building") }
                    )

                    Spacer(Modifier.height(10.dp))

                    Text("Details", fontWeight = FontWeight.Bold, color = UserUI.DarkBlue)
                    OutlinedTextField(
                        value = details,
                        onValueChange = { details = it },
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        placeholder = { Text("Describe what happened...") }
                    )

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = onSend,
                        colors = ButtonDefaults.buttonColors(containerColor = UserUI.DangerRed),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("SEND", color = Color.White, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    )
}

@Composable
fun ReportSentDialog(onClose: () -> Unit) {
    SimpleCenterDialog(
        title = "Report Sent!",
        message = "Your report has been sent to campus security.",
        onClose = onClose
    )
}

@Composable
fun MarkSafeDialog(onClose: () -> Unit) {
    SimpleCenterDialog(
        title = "Thank you!",
        message = "Your check-in was recorded. Stay safe!",
        onClose = onClose
    )
}

@Composable
fun ReportDetailDialog(
    onClose: () -> Unit,
    onAddDetails: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {},
        title = null,
        text = {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.SoftGray)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Report Details", fontWeight = FontWeight.Black, color = UserUI.DarkBlue, fontSize = 18.sp)
                        Spacer(Modifier.weight(1f))
                        Text("✕", color = UserUI.DarkBlue, modifier = Modifier.padding(6.dp))
                    }

                    Spacer(Modifier.height(10.dp))

                    Text("Incident: Power outage", color = UserUI.DarkBlue, fontWeight = FontWeight.Bold)
                    Text("Location: Academic Building", color = UserUI.DarkBlue)
                    Text("Status: RESOLVED", color = UserUI.DarkBlue, fontWeight = FontWeight.SemiBold)

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "Notes: Security confirmed the cause and restored power. Avoid elevators.",
                        color = UserUI.DarkBlue
                    )

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = onAddDetails,
                        colors = ButtonDefaults.buttonColors(containerColor = UserUI.DangerRed),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("ADD DETAILS", color = Color.White, fontWeight = FontWeight.Black)
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onClose,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("CLOSE", fontWeight = FontWeight.Bold, color = UserUI.DarkBlue)
                    }
                }
            }
        }
    )
}

@Composable
private fun SimpleCenterDialog(
    title: String,
    message: String,
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {},
        title = null,
        text = {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.SoftGray)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(title, fontWeight = FontWeight.Black, color = UserUI.DarkBlue, fontSize = 20.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(message, color = UserUI.DarkBlue)
                    Spacer(Modifier.height(14.dp))
                    Button(
                        onClick = onClose,
                        colors = ButtonDefaults.buttonColors(containerColor = UserUI.DangerRed),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("OK", color = Color.White, fontWeight = FontWeight.Black)
                    }
                }
            }
        }
    )
}
