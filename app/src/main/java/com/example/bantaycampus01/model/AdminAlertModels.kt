package com.example.bantaycampus01.model

import androidx.compose.ui.graphics.Color

enum class AlertStatusOpt(val label: String, val dot: Color) {
    SENT("Sent", Color(0xFFF4B400)),
    ACK("Acknowledged", Color(0xFFF4B400)),
    RESPONDING("Responding", Color(0xFFF4B400)),
    RESOLVED("Resolved", Color(0xFF29C65E))
}

enum class CampusRiskOpt(val label: String, val dot: Color) {
    SAFE("🟢 Safe", Color(0xFF29C65E)),
    MODERATE("🟡 Moderate", Color(0xFFF4B400)),
    HIGH("🟠 High", Color(0xFFFF8A00)),
    CRITICAL("🔴 Critical", Color(0xFFE53935))
}