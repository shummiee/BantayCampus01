package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun UserSosConfirmDialog(
    modifier: Modifier = Modifier,

    onClose: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = null,
        text = {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.SoftGray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SOS!",
                        fontWeight = FontWeight.Black,
                        color = UserUI.DangerRed,
                        fontSize = 26.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Are you sure you want to send an emergency alert to campus security?\n\n" +
                                "This will immediately notify responders and share your location.",
                        color = UserUI.DarkBlue,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = UserUI.DangerRed),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "YES, SEND SOS",
                    color = Color.White,
                    fontWeight = FontWeight.Black
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onClose,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "CANCEL",
                    fontWeight = FontWeight.Bold,
                    color = UserUI.DarkBlue
                )
            }
        }
    )
}

@Composable
fun SosSentDialog(
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = null,
        text = {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.SoftGray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SOS Sent!",
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Campus security has been notified.",
                        color = UserUI.DarkBlue,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = UserUI.DangerRed),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "OK",
                    color = Color.White,
                    fontWeight = FontWeight.Black
                )
            }
        },
        dismissButton = {}
    )
}