package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val nowMillis = System.currentTimeMillis()

        val formattedTimestamp = SimpleDateFormat(
            "MMM d, yyyy - h:mm a",
            Locale.getDefault()
        ).format(Date(nowMillis))

        val locationText = "Lat: $latitude, Lng: $longitude"

        val sosData = hashMapOf(
            "reportId" to sosRef.id,
            "userId" to userId,
            "userName" to userName,
            "idNumber" to userIdNumber,
            "message" to message,
            "timestamp" to formattedTimestamp,
            "createdAt" to nowMillis,
            "status" to "PENDING",
            "latitude" to latitude,
            "longitude" to longitude,
            "location" to locationText,
            "googleMapsLink" to "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
        )

        sosRef.set(sosData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}