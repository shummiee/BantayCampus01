package com.example.bantaycampus01.screens.admin.popups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.SubTextLabel
import com.example.bantaycampus01.ui.theme.TextBoxBg
import com.example.bantaycampus01.ui.theme.TextOnDark
import com.example.bantaycampus01.ui.theme.TextOnWhite
import com.example.bantaycampus01.ui.theme.White

@Composable
fun AdminAdvisoryDialog(
    show: Boolean,
    tempAdvisory: String,
    onTempAdvisoryChange: (String) -> Unit,
    onUpdate: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    val dialogScroll = rememberScrollState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .heightIn(max = 620.dp),
            shape = RoundedCornerShape(18.dp),
            color = White,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(dialogScroll)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(TextBoxBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("i", fontWeight = androidx.compose.ui.text.font.FontWeight.Black, color = TextOnWhite)
                    }

                    Spacer(modifier = Modifier.size(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Campus Security Advisory",
                            fontSize = 14.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                            color = TextOnWhite,
                            lineHeight = 14.sp
                        )
                        Text(
                            text = "Now",
                            fontSize = 11.sp,
                            color = SubTextLabel
                        )
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = TextOnWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = tempAdvisory,
                    onValueChange = onTempAdvisoryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    placeholder = { Text("Type campus advisory here...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onUpdate,
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrayBlue),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "UPDATE",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                            fontSize = 12.sp,
                            color = TextOnDark
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrayBlue),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "CANCEL",
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Black,
                            fontSize = 12.sp,
                            color = TextOnDark
                        )
                    }
                }
            }
        }
    }
}