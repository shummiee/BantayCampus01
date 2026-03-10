package com.example.bantaycampus01.model

data class ReportModel(
    val reportId: String = "",
    val userId: String = "",
    val userName: String = "",
    val idNumber: String = "",
    val incidentType: String = "",
    val location: String = "",
    val description: String = "",
    val urgency: String = "",
    val status: String = "PENDING",
    val createdAt: Long = System.currentTimeMillis()
)
