package com.example.bantaycampus01.model

import androidx.compose.ui.graphics.Color
import com.example.bantaycampus01.model.AlertCategory

data class UserAlertItem(
    val type: AlertCategory,
    val title: String,
    val timeRight: String,
    val location: String,
    val statusLabel: String,
    val statusColor: Color,
    val messagePreview: String
)
