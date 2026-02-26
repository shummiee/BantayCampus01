package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.*


@Composable
fun SendReportScreen(
    onClose: () -> Unit
) {
    var showSent by remember { mutableStateOf(false) }

    // form state (UI only)
    var category by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("Low") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.35f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(Modifier.fillMaxWidth()) {
                Text(
                    text = "✕",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp)
                        .clickable { onClose() },
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )

                val scroll = rememberScrollState()

                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .padding(top = 10.dp)
                        .verticalScroll(scroll) // ✅ scroll within modal
                ) {

                    Text(
                        text = "SEND REPORT",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue
                    )

                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = Color(0xFFE5E5E5))

                    Spacer(Modifier.height(12.dp))

                    Label("Category")
                    TextFieldWire(
                        value = category,
                        onValueChange = { category = it },
                        height = 46.dp,
                        trailing = {
                            Image(
                                painter = painterResource(R.drawable.ic_dropdown_arrow),
                                contentDescription = "Dropdown",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    )

                    Spacer(Modifier.height(10.dp))

                    Label("Location")
                    TextFieldWire(
                        value = location,
                        onValueChange = { location = it },
                        height = 46.dp
                    )

                    Spacer(Modifier.height(10.dp))

                    Label("Description")
                    TextFieldWire(
                        value = description,
                        onValueChange = { description = it },
                        height = 110.dp,
                        minLines = 4
                    )

                    Spacer(Modifier.height(10.dp))

                    Label("Urgency Level")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        UrgencyOption("Low", urgency) { urgency = "Low" }
                        UrgencyOption("Medium", urgency) { urgency = "Medium" }
                        UrgencyOption("High", urgency) { urgency = "High" }
                    }

                    Spacer(Modifier.height(10.dp))

                    Label("Upload Image")
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = UserUI.LightCard)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Choose file",
                                color = UserUI.DarkBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(Modifier.weight(1f))
                            Text("🖼️", fontSize = 18.sp)
                        }
                    }

                    Spacer(Modifier.height(14.dp))

                    // SUBMIT button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(UserUI.DangerRed, RoundedCornerShape(50))
                            .clickable { showSent = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SUBMIT REPORT",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(Modifier.height(10.dp))
                }
            }
        }

        // "Report sent" modal
        if (showSent) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            text = "✕",
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(14.dp)
                                .clickable {
                                    showSent = false
                                    onClose()
                                },
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Black,
                            color = UserUI.DarkBlue
                        )

                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(6.dp))
                            Text(text = "✅", fontSize = 36.sp)
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "REPORT HAS BEEN SENT!",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Black,
                                color = UserUI.DarkBlue,
                                textAlign = TextAlign.Center
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Campus security has been notified.\nPlease stay safe and wait for updates.",
                                fontSize = 18.sp,
                                color = UserUI.DarkBlue,
                                textAlign = TextAlign.Center,
                                lineHeight = 24.sp
                            )

                            Spacer(Modifier.height(14.dp))

                            Box(
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(46.dp)
                                    .background(UserUI.DarkBlue, RoundedCornerShape(50))
                                    .clickable {
                                        showSent = false
                                        onClose()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "CLOSE",
                                    color = Color.White,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 16.sp
                                )
                            }

                            Spacer(Modifier.height(6.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = UserUI.DarkBlue
    )
}

@Composable
private fun TextFieldWire(
    value: String,
    onValueChange: (String) -> Unit,
    height: Dp,
    minLines: Int = 1,
    trailing: (@Composable (() -> Unit))? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        minLines = minLines,
        trailingIcon = trailing,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = UserUI.LightCard,
            unfocusedContainerColor = UserUI.LightCard,
            focusedBorderColor = UserUI.DarkBlue,
            unfocusedBorderColor = UserUI.DarkBlue,
            cursorColor = UserUI.DarkBlue
        )
    )
}

@Composable
private fun UrgencyOption(
    label: String,
    selected: String,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onSelect() }
    ) {
        RadioButton(
            selected = selected == label,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(selectedColor = UserUI.DarkBlue)
        )
        Text(label, color = UserUI.DarkBlue, fontWeight = FontWeight.SemiBold)
    }
}
