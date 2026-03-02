package com.example.bantaycampus01.screens.Admin.PopUps

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.TextOnDark
import com.example.bantaycampus01.ui.theme.TextOnWhite
import com.example.bantaycampus01.ui.theme.White

@Composable
fun AdminCampusStatusDialog(
    show: Boolean,

    statusOptions: List<String>,
    statusExpanded: Boolean,
    onStatusExpandedChange: (Boolean) -> Unit,

    tempCampusStatus: String,
    onTempCampusStatusChange: (String) -> Unit,

    tempCampusNote: String,
    onTempCampusNoteChange: (String) -> Unit,

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
                Text(
                    text = "UPDATE CAMPUS STATUS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "STATUS",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        color = TextOnWhite
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    androidx.compose.foundation.layout.Box(modifier = Modifier.weight(1f)) {
                        OutlinedButton(
                            onClick = { onStatusExpandedChange(true) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                        ) {
                            Text(
                                text = tempCampusStatus,
                                fontSize = 12.sp,
                                color = TextOnWhite,
                                modifier = Modifier.weight(1f)
                            )
                            Text("▾", color = TextOnWhite)
                        }

                        DropdownMenu(
                            expanded = statusExpanded,
                            onDismissRequest = { onStatusExpandedChange(false) }
                        ) {
                            statusOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option, fontSize = 12.sp) },
                                    onClick = {
                                        onTempCampusStatusChange(option)
                                        onStatusExpandedChange(false)
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Note:",
                    fontSize = 12.sp,
                    color = TextOnWhite,
                    fontStyle = FontStyle.Italic
                )

                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = tempCampusNote,
                    onValueChange = onTempCampusNoteChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(95.dp),
                    placeholder = { Text("Enter update note...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(14.dp)
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
                            fontWeight = FontWeight.Black,
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
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = TextOnDark
                        )
                    }
                }
            }
        }
    }
}