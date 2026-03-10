package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bantaycampus01.model.ReportModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ReportViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun submitReport(
        incidentType: String,
        location: String,
        description: String,
        urgency: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

        val user = auth.currentUser ?: return

        val reportRef = db.collection("reports").document()

        val report = ReportModel(
            reportId = reportRef.id,
            userId = user.uid,
            incidentType = incidentType,
            location = location,
            description = description,
            urgency = urgency
        )

        reportRef.set(report)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }
}