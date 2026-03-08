package com.example.bantaycampus01.repository

import com.example.bantaycampus01.model.SosAlert
import com.google.firebase.firestore.FirebaseFirestore

class SosRepository {
    private val db = FirebaseFirestore.getInstance()

    fun sendSOS(
        sosAlert: SosAlert,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        val sosRef = db.collection("sos_alerts").document()

        val sosWithId = sosAlert.copy(id = sosRef.id)

        sosRef.set(sosWithId)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}