package com.example.bantaycampus01.screens.User.Menu.PopUps

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun ReportSentDialog(
    show: Boolean,
    onDismiss: () -> Unit
) {
    if (!show) return

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp),
            shape = RoundedCornerShape(18.dp),
            color = androidx.compose.ui.graphics.Color.White,
            shadowElevation = 10.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                // Close (X) top-right
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = UserUI.DarkBlue
                    )
                }

                // Big centered message
                Text(
                    text = "Your report has\nbeen sent to\ncampus security.",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp, bottom = 18.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}