package com.example.bantaycampus01.model

data class SosAlert(
    val id: String = "",
    val userId: String = "",
    val userName: String = "",
    val message: String = "SOS Emergency",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "PENDING" // PENDING, DISPATCHED, RESOLVED
)
