package com.example.bantaycampus01.model

import androidx.compose.ui.graphics.Color

enum class CampusRiskLevel(val label: String, val dotColor: Color) {
    CRITICAL("CRITICAL", Color(0xFFE53935)), // 🔴
    HIGH("HIGH", Color(0xFFFF7A00)),        // 🟠
    MODERATE("MODERATE", Color(0xFFF4B400)),// 🟡
    SAFE("SAFE", Color(0xFF29C65E))         // 🟢
}