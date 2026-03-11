package com.example.bantaycampus01.model

import androidx.compose.ui.graphics.Color

data class CheckInItem(
    val name: String,
    val rightTime: String,
    val statusLabel: String,
    val statusColor: Color,
    val lastCheckIn: String
)