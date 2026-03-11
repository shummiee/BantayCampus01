package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bantaycampus01.model.SosAlert
import com.google.firebase.firestore.FirebaseFirestore

class SosViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun sendSOS(
        userId: String,
        userName: String,
        userIdNumber: String,
        latitude: Double,
        longitude: Double,
        message: String = "SOS Emergency",
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val sosRef = db.collection("sos_alerts").document()
        val formattedTimestamp = com.google.firebase.Timestamp.now().toString()

        val sosData = hashMapOf(
            "reportId" to sosRef.id,
            "userId" to userId,
            "userName" to userName,
            "idNumber" to userIdNumber,
            "message" to message,
            "timestamp" to formattedTimestamp,
            "status" to "PENDING",
            "latitude" to latitude,
            "longitude" to longitude,
            "googleMapsLink" to "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
        )

        sosRef.set(sosData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}