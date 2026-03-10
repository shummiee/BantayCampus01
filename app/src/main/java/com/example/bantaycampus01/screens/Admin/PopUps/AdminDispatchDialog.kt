package com.example.bantaycampus01.screens.Admin.PopUps

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun AdminDispatchDialog(
    show: Boolean,
    alertIdLabel: String,

    guardA: Boolean, onGuardAChange: (Boolean) -> Unit,
    guardB: Boolean, onGuardBChange: (Boolean) -> Unit,
    guardC: Boolean, onGuardCChange: (Boolean) -> Unit,
    guardD: Boolean, onGuardDChange: (Boolean) -> Unit,
    guardE: Boolean, onGuardEChange: (Boolean) -> Unit,
    guardF: Boolean, onGuardFChange: (Boolean) -> Unit,
    medicalUnit: Boolean, onMedicalUnitChange: (Boolean) -> Unit,

    dispatchNote: String,
    onDispatchNoteChange: (String) -> Unit,

    onDispatch: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    val dialogScroll = rememberScrollState()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .heightIn(max = 620.dp),
            shape = RoundedCornerShape(16.dp),
            color = White,
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(dialogScroll)
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Alert ID: $alertIdLabel",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = White,
                        modifier = Modifier
                            .background(
                                androidx.compose.ui.graphics.Color(0xFF2E3B55),
                                RoundedCornerShape(6.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close",
                            tint = TextOnWhite
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Dispatch Response Team",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextOnWhite
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "Are you sure you want to\nmark this alert as\nResponding and assign\na response team?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Assign Response Team:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextOnWhite
                )

                Spacer(Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SmallCheckRow("Guard A", guardA, onGuardAChange)
                    SmallCheckRow("Guard B", guardB, onGuardBChange)
                    SmallCheckRow("Guard C", guardC, onGuardCChange)
                    SmallCheckRow("Guard D", guardD, onGuardDChange)
                    SmallCheckRow("Guard E", guardE, onGuardEChange)
                    SmallCheckRow("Guard F", guardF, onGuardFChange)
                    SmallCheckRow("Medical Unit", medicalUnit, onMedicalUnitChange)
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Note (optional)",
                    fontSize = 12.sp,
                    color = TextOnWhite,
                    fontStyle = FontStyle.Italic
                )

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = dispatchNote,
                    onValueChange = onDispatchNoteChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp),
                    placeholder = { Text("Add dispatch note...", fontSize = 12.sp) },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onDispatch,
                        colors = ButtonDefaults.buttonColors(containerColor = DarkGrayBlue),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "DISPATCH",
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp,
                            color = TextOnDark
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

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

@Composable
private fun SmallCheckRow(
    text: String,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onChecked
        )
        Spacer(Modifier.width(2.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = TextOnWhite
        )
    }
}