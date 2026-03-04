package com.example.bantaycampus01.screens.User.Menu.PopUps

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun MarkSafeDialog(
    show: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    SimpleCenterDialog(show = show, onDismiss = onDismiss) {
        Text(
            text = "Mark as Safe?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = UserUI.DarkBlue
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Are you sure you want to mark yourself as safe?",
            color = UserUI.DarkBlue
        )

        Spacer(Modifier.height(14.dp))

        Row(Modifier.fillMaxWidth()) {
            Button(
                onClick = onConfirm,
                modifier = Modifier.weight(1f)
            ) { Text("YES", fontWeight = FontWeight.Black) }

            Spacer(Modifier.width(10.dp))

            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.weight(1f)
            ) { Text("CANCEL", fontWeight = FontWeight.Black) }
        }
    }
}