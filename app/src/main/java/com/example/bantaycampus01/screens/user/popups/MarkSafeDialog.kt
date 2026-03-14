package com.example.bantaycampus01.screens.user.menu.PopUps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun MarkSafeDialog(
    show: Boolean,
    onConfirm: () -> Unit,  // used as CLOSE action
    onDismiss: () -> Unit
) {
    SimpleCenterDialog(show = show, onDismiss = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "THANK YOU FOR CHECKING IN.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "You are now marked as safe.\nStay alert and take care. Help is\nalways available if needed.",
                fontSize = 14.sp,
                color = UserUI.DarkBlue,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = onConfirm,
                modifier = Modifier
                    .width(130.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = UserUI.DarkBlue
                ),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "CLOSE",
                    fontWeight = FontWeight.Black,
                    fontSize = 12.sp
                )
            }
        }
    }
}