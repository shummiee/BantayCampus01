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

    fun markUserSafe(
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onFailure(Exception("User is not logged in."))
            return
        }

        db.collection("users")
            .document(currentUser.uid)
            .update(
                mapOf(
                    "lastCheckIn" to System.currentTimeMillis(),
                    "safetyStatus" to "SAFE"
                )
            )
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun refreshSafetyStatusIfExpired(
        onComplete: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onFailure(Exception("User is not logged in."))
            return
        }

        db.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (!document.exists()) {
                    onFailure(Exception("User document not found."))
                    return@addOnSuccessListener
                }

                val lastCheckIn = document.getLong("lastCheckIn")
                val currentStatus = document.getString("safetyStatus") ?: "UNKNOWN"

                if (lastCheckIn == null) {
                    if (currentStatus != "UNKNOWN") {
                        db.collection("users")
                            .document(currentUser.uid)
                            .update("safetyStatus", "UNKNOWN")
                            .addOnSuccessListener { onComplete("UNKNOWN") }
                            .addOnFailureListener { onFailure(it) }
                    } else {
                        onComplete("UNKNOWN")
                    }
                    return@addOnSuccessListener
                }

                val now = System.currentTimeMillis()
                val twentyFourHours = 24 * 60 * 60 * 1000L

                if ((now - lastCheckIn) > twentyFourHours) {
                    if (currentStatus != "UNKNOWN") {
                        db.collection("users")
                            .document(currentUser.uid)
                            .update("safetyStatus", "UNKNOWN")
                            .addOnSuccessListener { onComplete("UNKNOWN") }
                            .addOnFailureListener { onFailure(it) }
                    } else {
                        onComplete("UNKNOWN")
                    }
                } else {
                    if (currentStatus != "SAFE") {
                        db.collection("users")
                            .document(currentUser.uid)
                            .update("safetyStatus", "SAFE")
                            .addOnSuccessListener { onComplete("SAFE") }
                            .addOnFailureListener { onFailure(it) }
                    } else {
                        onComplete("SAFE")
                    }
                }
            }
            .addOnFailureListener { onFailure(it) }
    }

    fun getLastCheckInText(
        lastCheckIn: Long?
    ): String {
        if (lastCheckIn == null) return "No recent check-in"

        val now = System.currentTimeMillis()
        val diff = now - lastCheckIn

        val minute = 60 * 1000L
        val hour = 60 * minute
        val day = 24 * hour

        return when {
            diff < minute -> "Last check-in just now"
            diff < hour -> "Last check-in ${diff / minute} minute(s) ago"
            diff < day -> "Last check-in ${diff / hour} hour(s) ago"
            else -> "Last check-in more than 24 hours ago"
        }
    }
}