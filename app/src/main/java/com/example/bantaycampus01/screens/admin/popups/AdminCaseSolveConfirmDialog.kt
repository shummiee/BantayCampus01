package com.example.bantaycampus01.screens.admin.popups

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.ui.theme.*

@Composable
fun AdminCaseSolveConfirmDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    if (!show) return

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
                    .padding(18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = TextOnWhite
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Can you confirm that\nthis case has been\nresolved?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier
                            .height(42.dp)
                            .width(110.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkGrayBlue
                        )
                    ) {
                        Text(
                            text = "CONFIRM",
                            fontWeight = FontWeight.Bold,
                            color = TextOnDark
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Button(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(999.dp),
                        modifier = Modifier
                            .height(42.dp)
                            .width(110.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TextBoxBg
                        )
                    ) {
                        Text(
                            text = "CANCEL",
                            fontWeight = FontWeight.Bold,
                            color = TextOnWhite
                        )
                    }
                }
            }
        }
    }
}