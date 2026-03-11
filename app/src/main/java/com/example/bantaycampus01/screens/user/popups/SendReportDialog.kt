package com.example.bantaycampus01.screens.user.menu.PopUps

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bantaycampus01.partials.user.UserUI

enum class UrgencyLevel(val label: String) { MODERATE("Moderate"), HIGH("High"), URGENT("Urgent") }

@Composable
fun SendReportDialog(
    show: Boolean,

    // Dropdown
    incidentExpanded: Boolean,
    onIncidentExpandedChange: (Boolean) -> Unit,
    incidentOptions: List<String>,
    selectedIncident: String,
    onSelectedIncidentChange: (String) -> Unit,

    // Fields
    location: String,
    onLocationChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,

    // Urgency
    urgency: UrgencyLevel,
    onUrgencyChange: (UrgencyLevel) -> Unit,

    // Upload
    onUploadClick: () -> Unit,

    // Actions
    onSubmit: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!show) return

    val scroll = rememberScrollState()

    val modalShape = RoundedCornerShape(18.dp)
    val fieldShape = RoundedCornerShape(4.dp)

    // “CSS-like” colors based on screenshot
    val modalBg = Color.White
    val labelColor = UserUI.DarkBlue
    val requiredColor = Color(0xFFB50000)
    val fieldBg = Color(0xFFEDEDED)
    val fieldBorder = Color(0xFFB7B7B7)
    val submitBg = UserUI.DarkBlue

    fun requiredLabel(text: String) = buildAnnotatedString {
        withStyle(SpanStyle(color = labelColor, fontWeight = FontWeight.Bold)) { append(text) }
        append(" ")
        withStyle(SpanStyle(color = requiredColor, fontWeight = FontWeight.Black)) { append("*") }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .heightIn(max = 640.dp),
            shape = modalShape,
            color = modalBg,
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scroll)
                    .padding(horizontal = 18.dp, vertical = 16.dp)
            ) {
                // Header row (SEND REPORT + X)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SEND REPORT",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = labelColor,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "✕",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = labelColor,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable { onDismiss() }
                    )
                }

                Spacer(Modifier.height(12.dp))

                // Type of Incident *
                Text(text = requiredLabel("Type of Incident"), fontSize = 14.sp)

                Spacer(Modifier.height(6.dp))

                // Dropdown look (matches screenshot: small box + arrow)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .clip(fieldShape)
                        .background(fieldBg)
                        .border(1.dp, fieldBorder, fieldShape)
                        .clickable { onIncidentExpandedChange(true) }
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (selectedIncident.isBlank()) "-" else selectedIncident,
                            color = labelColor,
                            fontSize = 13.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Open incident types",
                            tint = labelColor
                        )
                    }

                    DropdownMenu(
                        expanded = incidentExpanded,
                        onDismissRequest = { onIncidentExpandedChange(false) }
                    ) {
                        incidentOptions.forEach { opt ->
                            DropdownMenuItem(
                                text = { Text(opt) },
                                onClick = {
                                    onSelectedIncidentChange(opt)
                                    onIncidentExpandedChange(false)
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // Location *
                Text(text = requiredLabel("Location"), fontSize = 14.sp)
                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = location,
                    onValueChange = onLocationChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    placeholder = {
                        Text(
                            "Where the incident located within the\ncampus?",
                            fontSize = 12.sp,
                            color = labelColor.copy(alpha = 0.65f)
                        )
                    },
                    singleLine = false,
                    shape = fieldShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = fieldBg,
                        unfocusedContainerColor = fieldBg,
                        focusedBorderColor = fieldBorder,
                        unfocusedBorderColor = fieldBorder,
                        cursorColor = labelColor,
                        focusedTextColor = labelColor,
                        unfocusedTextColor = labelColor
                    )
                )

                Spacer(Modifier.height(12.dp))

                // Description *
                Text(text = requiredLabel("Description"), fontSize = 14.sp)
                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = onDescriptionChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    placeholder = {
                        Text(
                            "What happened? Describe the incident.",
                            fontSize = 12.sp,
                            color = labelColor.copy(alpha = 0.65f)
                        )
                    },
                    singleLine = false,
                    shape = fieldShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = fieldBg,
                        unfocusedContainerColor = fieldBg,
                        focusedBorderColor = fieldBorder,
                        unfocusedBorderColor = fieldBorder,
                        cursorColor = labelColor,
                        focusedTextColor = labelColor,
                        unfocusedTextColor = labelColor
                    )
                )

                Spacer(Modifier.height(12.dp))

                // Urgency Level *
                Text(text = requiredLabel("Urgency Level"), fontSize = 14.sp)
                Spacer(Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UrgencyRadio(
                        label = UrgencyLevel.MODERATE.label,
                        selected = urgency == UrgencyLevel.MODERATE,
                        onClick = { onUrgencyChange(UrgencyLevel.MODERATE) },
                        color = labelColor
                    )
                    Spacer(Modifier.width(12.dp))
                    UrgencyRadio(
                        label = UrgencyLevel.HIGH.label,
                        selected = urgency == UrgencyLevel.HIGH,
                        onClick = { onUrgencyChange(UrgencyLevel.HIGH) },
                        color = labelColor
                    )
                    Spacer(Modifier.width(12.dp))
                    UrgencyRadio(
                        label = UrgencyLevel.URGENT.label,
                        selected = urgency == UrgencyLevel.URGENT,
                        onClick = { onUrgencyChange(UrgencyLevel.URGENT) },
                        color = labelColor
                    )
                }

                Spacer(Modifier.height(12.dp))

                // Add Photo (optional)
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = labelColor, fontWeight = FontWeight.Bold)) {
                            append("Add Photo ")
                        }
                        withStyle(SpanStyle(color = labelColor.copy(alpha = 0.7f))) {
                            append("(optional)")
                        }
                    },
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(6.dp))

                // Upload row (small button + icon)
                OutlinedButton(
                    onClick = onUploadClick,
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = fieldBg,
                        contentColor = labelColor
                    ),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("Upload Image", fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    Spacer(Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Filled.Upload,
                        contentDescription = "Upload",
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                // SUBMIT centered pill button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onSubmit,
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = submitBg),
                        contentPadding = PaddingValues(horizontal = 34.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = "SUBMIT",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }
                }

                Spacer(Modifier.height(6.dp))
            }
        }
    }
}

@Composable
private fun UrgencyRadio(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = color,
                unselectedColor = color.copy(alpha = 0.55f)
            )
        )
        Text(text = label, fontSize = 12.sp, color = color)
    }
}