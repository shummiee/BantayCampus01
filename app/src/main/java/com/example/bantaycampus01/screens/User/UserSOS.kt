package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.*

@Composable
fun SosConfirmDialog(
    onClose: () -> Unit,
    onConfirm: () -> Unit
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
                    Text(
                        "SOS!",
                        fontWeight = FontWeight.Black,
                        color = UserUI.DangerRed,
                        fontSize = 24.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "Are you sure you want to send an emergency alert to campus security?\n\n" +
                                "This will immediately notify responders and share your location.",
                        color = UserUI.DarkBlue,
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(14.dp))

                    Button(
                        onClick = onConfirm,
                        colors = ButtonDefaults.buttonColors(containerColor = UserUI.DangerRed),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("YES, SEND SOS", color = Color.White, fontWeight = FontWeight.Black)
                    }

                    Spacer(Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = onClose,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("CANCEL", fontWeight = FontWeight.Bold, color = UserUI.DarkBlue)
                    }
                }
            }
        }
    )
}

@Composable
fun SosSentDialog(onClose: () -> Unit) {
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
                    Text("SOS Sent!", fontWeight = FontWeight.Black, color = UserUI.DarkBlue, fontSize = 20.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Campus security has been notified.", color = UserUI.DarkBlue)
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
