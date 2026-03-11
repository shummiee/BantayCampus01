package com.example.bantaycampus01.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bantaycampus01.model.ReportModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class UserLatestAlert(
    val isSos: Boolean = false,
    val reportId: String = "",
    val status: String = "PENDING",
    val category: String = "",
    val createdAt: Long = 0L,
    val location: String = "",
    val description: String = "",
    val coordinates: String = ""
)

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

        val user = auth.currentUser

        if (user == null) {
            onFailure(Exception("User is not logged in."))
            return
        }

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

    fun fetchUserReports(
        onSuccess: (List<ReportModel>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onFailure(Exception("User is not logged in."))
            return
        }

        android.util.Log.d("ReportDebug", "Current UID: ${currentUser.uid}")

        db.collection("reports")
            .get()
            .addOnSuccessListener { result ->
                for (document in result.documents) {
                    android.util.Log.d(
                        "ReportDebug",
                        "Doc ID=${document.id}, userId=${document.getString("userId")}, incidentType=${document.getString("incidentType")}"
                    )
                }

                val reports = result.documents.mapNotNull { document ->
                    document.toObject(ReportModel::class.java)
                }.filter { report ->
                    report.userId == currentUser.uid
                }

                android.util.Log.d("ReportDebug", "Matched reports count: ${reports.size}")
                onSuccess(reports)
            }
            .addOnFailureListener { e ->
                android.util.Log.e("ReportDebug", "fetchUserReports failed", e)
                onFailure(e)
            }
    }

    fun getTimeAgo(createdAt: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - createdAt

        val minute = 60 * 1000L
        val hour = 60 * minute
        val day = 24 * hour
        val week = 7 * day

        return when {
            diff < minute -> "Just now"
            diff < hour -> "${diff / minute} minute(s) ago"
            diff < day -> "${diff / hour} hour(s) ago"
            diff < week -> "${diff / day} day(s) ago"
            else -> "${diff / week} week(s) ago"
        }
    }

    fun getReadableStatus(status: String): String {
        return when (status.uppercase()) {
            "PENDING" -> "Status: Pending 🟠"
            "RESPONDING" -> "Status: Responding 🟡"
            "RESOLVED" -> "Status: Resolved 🟢"
            else -> "Status: $status"
        }
    }

    fun fetchLatestUserReport(
        onSuccess: (ReportModel?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onFailure(Exception("User is not logged in."))
            return
        }

        db.collection("reports")
            .whereEqualTo("userId", currentUser.uid)
            .get()
            .addOnSuccessListener { result ->
                val latestReport = result.documents
                    .mapNotNull { it.toObject(ReportModel::class.java) }
                    .maxByOrNull { it.createdAt }

                onSuccess(latestReport)
            }
            .addOnFailureListener { e ->
                android.util.Log.e("ReportViewModel", "fetchLatestUserReport failed", e)
                onFailure(e)
            }
    }

    fun fetchLatestUserAlert(
        onSuccess: (UserLatestAlert?) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            onFailure(Exception("User is not logged in."))
            return
        }

        db.collection("reports")
            .whereEqualTo("userId", currentUser.uid)
            .get()
            .addOnSuccessListener { reportResult ->

                val latestReport = reportResult.documents
                    .mapNotNull { it.toObject(ReportModel::class.java) }
                    .maxByOrNull { it.createdAt }
                    ?.let { report ->
                        UserLatestAlert(
                            isSos = false,
                            reportId = report.reportId,
                            status = report.status,
                            category = report.incidentType,
                            createdAt = report.createdAt,
                            location = report.location,
                            description = report.description,
                            coordinates = ""
                        )
                    }

                db.collection("sos_alerts")
                    .whereEqualTo("userId", currentUser.uid)
                    .get()
                    .addOnSuccessListener { sosResult ->

                        val latestSos = sosResult.documents
                            .maxByOrNull { it.getLong("createdAt") ?: 0L }
                            ?.let { doc ->
                                val latitude = doc.getDouble("latitude")
                                val longitude = doc.getDouble("longitude")

                                val coordinatesText =
                                    if (latitude != null && longitude != null) {
                                        "Lat: $latitude, Lng: $longitude"
                                    } else {
                                        ""
                                    }

                                UserLatestAlert(
                                    isSos = true,
                                    reportId = doc.getString("reportId") ?: doc.id,
                                    status = doc.getString("status") ?: "PENDING",
                                    category = "SOS Alert",
                                    createdAt = doc.getLong("createdAt") ?: 0L,
                                    location = doc.getString("location") ?: "",
                                    description = doc.getString("message") ?: "SOS Emergency",
                                    coordinates = coordinatesText
                                )
                            }

                        val latestAlert = when {
                            latestReport == null && latestSos == null -> null
                            latestReport == null -> latestSos
                            latestSos == null -> latestReport
                            latestSos.createdAt > latestReport.createdAt -> latestSos
                            else -> latestReport
                        }

                        onSuccess(latestAlert)
                    }
                    .addOnFailureListener { e ->
                        android.util.Log.e("ReportViewModel", "fetchLatestUserAlert SOS failed", e)
                        onFailure(e)
                    }
            }
            .addOnFailureListener { e ->
                android.util.Log.e("ReportViewModel", "fetchLatestUserAlert reports failed", e)
                onFailure(e)
            }
    }
}