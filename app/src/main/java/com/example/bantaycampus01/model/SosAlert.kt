package com.example.bantaycampus01.model

data class SosAlert(
    val reportId: String = "",
    val userId: String = "",
    val userName: String = "",
    val idNumber: String = "",
    val message: String = "SOS Emergency",
    val timestamp: String = "",
    val status: String = "PENDING"
)
