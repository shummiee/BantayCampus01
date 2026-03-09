package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bantaycampus01.model.SosAlert
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class SosViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    fun sendSOS(
        userId: String,
        userName: String,
        userIdNumber: String,
        message: String = "SOS Emergency",
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val sosRef = db.collection("sos_alerts").document()
        val formattedTimestamp = com.google.firebase.Timestamp.now().toString()

        val sosAlert = SosAlert(
            reportId = sosRef.id,
            userId = userId,
            userName = userName,
            idNumber = userIdNumber,
            message = message,
            timestamp = formattedTimestamp,
            status = "PENDING"
        )

        sosRef.set(sosAlert)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}