package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.google.firebase.firestore.FirebaseFirestore

class AdminAlertsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    val alerts = mutableStateListOf<Map<String, Any>>()

    fun loadAlerts() {

        alerts.clear()

        // LOAD REPORTS
        db.collection("reports")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    alerts.add(doc.data)
                }
            }

        // LOAD SOS ALERTS
        db.collection("sos_alerts")
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    alerts.add(doc.data)
                }
            }
    }
}