package com.example.bantaycampus01.screens.User.Menu.PopUps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
fun ChangePasswordDialog(
    show: Boolean,

    currentPassword: String,
    onCurrentPasswordChange: (String) -> Unit,

    newPassword: String,
    onNewPasswordChange: (String) -> Unit,

    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,

    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    val scroll = rememberScrollState()

    // CSS-like based on screenshot
    val modalShape = RoundedCornerShape(18.dp)
    val fieldShape = RoundedCornerShape(18.dp)

    val modalBg = Color.White
    val labelColor = UserUI.DarkBlue
    val fieldBg = Color(0xFFEDEDED)
    val fieldBorder = Color.Transparent // screenshot looks borderless
    val confirmBg = UserUI.DarkBlue
    val cancelBg = Color(0xFFD9D9D9)
    val cancelText = labelColor

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = modalShape,
            color = modalBg,
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scroll)
                    .padding(horizontal = 18.dp, vertical = 18.dp)
            ) {
                // Current Password
                Text(
                    text = "Current Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = labelColor
                )
                Spacer(Modifier.height(8.dp))

                PasswordField(
                    value = currentPassword,
                    onValueChange = onCurrentPasswordChange,
                    fieldBg = fieldBg,
                    fieldBorder = fieldBorder,
                    fieldShape = fieldShape
                )

                Spacer(Modifier.height(14.dp))

                // New Password
                Text(
                    text = "New Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = labelColor
                )
                Spacer(Modifier.height(8.dp))

                PasswordField(
                    value = newPassword,
                    onValueChange = onNewPasswordChange,
                    fieldBg = fieldBg,
                    fieldBorder = fieldBorder,
                    fieldShape = fieldShape
                )

                Spacer(Modifier.height(14.dp))

                // Confirm New Password
                Text(
                    text = "Confirm New Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = labelColor
                )
                Spacer(Modifier.height(8.dp))

                PasswordField(
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    fieldBg = fieldBg,
                    fieldBorder = fieldBorder,
                    fieldShape = fieldShape
                )

                Spacer(Modifier.height(18.dp))

                // Buttons row (CONFIRM + CANCEL)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = confirmBg),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = "CONFIRM",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    Button(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(22.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = cancelBg),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = "CANCEL",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = cancelText
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    fieldBg: Color,
    fieldBorder: Color,
    fieldShape: RoundedCornerShape
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        singleLine = true,
        shape = fieldShape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = fieldBg,
            unfocusedContainerColor = fieldBg,
            focusedBorderColor = fieldBorder,
            unfocusedBorderColor = fieldBorder,
            cursorColor = UserUI.DarkBlue,
            focusedTextColor = UserUI.DarkBlue,
            unfocusedTextColor = UserUI.DarkBlue
        )
    )
}