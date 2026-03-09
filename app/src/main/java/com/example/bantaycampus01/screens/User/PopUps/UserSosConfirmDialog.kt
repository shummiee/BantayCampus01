package com.example.bantaycampus01.screens.User.PopUps

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.viewmodel.AuthViewModel
import com.example.bantaycampus01.viewmodel.SosViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserSosConfirmDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    authViewModel: AuthViewModel = viewModel(),
    sosViewModel: SosViewModel = viewModel()
) {
    if (!show) return

    val dialogScroll = rememberScrollState()
    val context = LocalContext.current
    var showSosSentDialog by remember { mutableStateOf(false) }

    // Local state for the current user
    var currentUserName by remember { mutableStateOf<String?>(null) }
    var currentUserIdNumber by remember { mutableStateOf<String?>(null) }
    var currentUserId by remember { mutableStateOf<String?>(null) }

    // Fetch user profile once when dialog opens
    LaunchedEffect(Unit) {
        authViewModel.getUserProfile { name, email, contact, idNumber, department, role ->
            currentUserName = name
            currentUserIdNumber = idNumber
            currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        }
    }

    // Show the SOS Sent dialog if SOS was successfully sent
    if (showSosSentDialog) {
        UserSosSentDialog(
            show = true,
            onDismiss = {
                onDismiss()
            }
        )
        return
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .heightIn(max = 620.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(dialogScroll)
                    .padding(14.dp)
            ) {
                // --- Header ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "SOS ALERT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        modifier = Modifier
                            .background(UserUI.DangerRed, RoundedCornerShape(6.dp))
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

                Spacer(Modifier.height(10.dp))

                // --- Body ---
                Text(
                    text = "Send Emergency SOS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = UserUI.DarkBlue
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Are you sure you want to\nsend an emergency alert\nto campus security?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "This will notify responders immediately\nand share your location.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = UserUI.DarkBlue.copy(alpha = 0.75f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 16.sp
                )

                Spacer(Modifier.height(14.dp))

                // --- Buttons ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // SEND SOS Button
                    Button(
                        onClick = {
                            val userId = currentUserId
                            val userName = currentUserName
                            val userIdNumber = currentUserIdNumber
                            if (userId != null && userName != null && userIdNumber != null) {
                                sosViewModel.sendSOS(
                                    userId = userId,
                                    userName = userName,
                                    userIdNumber = userIdNumber,
                                    onSuccess = { showSosSentDialog = true },
                                    onFailure = {
                                        Toast.makeText(
                                            context,
                                            "SOS Failed: ${it.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            } else {
                                Toast.makeText(context, "User info not loaded", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = UserUI.DangerRed),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "SEND SOS",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // CANCEL Button
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = UserUI.DarkBlue),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "CANCEL",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}