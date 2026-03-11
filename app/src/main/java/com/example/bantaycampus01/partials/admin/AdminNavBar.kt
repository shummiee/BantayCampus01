package com.example.bantaycampus01.partials.admin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

data class AdminSosPopupData(
    val docId: String = "",
    val userName: String = "",
    val idNumber: String = "",
    val message: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val googleMapsLink: String = "",
    val status: String = "PENDING",
    val createdAt: Long = 0L
)

private const val SOS_PREFS = "admin_sos_popup_prefs"
private const val SOS_SHOWN_IDS = "shown_sos_ids"

@Composable
fun AdminNavBar(
    modifier: Modifier,
    navController: NavController
) {
    val context = LocalContext.current
    val db = remember { FirebaseFirestore.getInstance() }

    var showSosPopup by remember { mutableStateOf(false) }
    var latestSosAlert by remember { mutableStateOf<AdminSosPopupData?>(null) }

    val shownSosIds = remember {
        mutableStateListOf<String>().apply {
            addAll(loadShownSosIds(context))
        }
    }

    fun markSosAsShown(docId: String) {
        if (!shownSosIds.contains(docId)) {
            shownSosIds.add(docId)
            saveShownSosIds(context, shownSosIds.toSet())
        }
    }

    fun openMap(link: String, latitude: Double?, longitude: Double?) {
        val url = when {
            link.isNotBlank() -> link
            latitude != null && longitude != null ->
                "https://www.google.com/maps/search/?api=1&query=$latitude,$longitude"
            else -> ""
        }

        if (url.isBlank()) {
            Toast.makeText(context, "Location link not available.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open map.", Toast.LENGTH_SHORT).show()
        }
    }

    DisposableEffect(Unit) {
        val sosListener: ListenerRegistration =
            db.collection("sos_alerts")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) return@addSnapshotListener

                    val sosAlerts = snapshot?.documents?.map { doc ->
                        AdminSosPopupData(
                            docId = doc.id,
                            userName = doc.getString("userName") ?: "Unknown User",
                            idNumber = doc.getString("idNumber") ?: "No ID number",
                            message = doc.getString("message") ?: "SOS Emergency",
                            latitude = doc.getDouble("latitude"),
                            longitude = doc.getDouble("longitude"),
                            googleMapsLink = doc.getString("googleMapsLink") ?: "",
                            status = doc.getString("status") ?: "PENDING",
                            createdAt = doc.getLong("createdAt") ?: 0L
                        )
                    } ?: emptyList()

                    val newestUnseenSos = sosAlerts
                        .filter {
                            it.status.uppercase() == "PENDING" &&
                                    !shownSosIds.contains(it.docId)
                        }
                        .maxByOrNull { it.createdAt }

                    if (newestUnseenSos != null) {
                        latestSosAlert = newestUnseenSos
                        showSosPopup = true
                        markSosAsShown(newestUnseenSos.docId)
                    }
                }

        onDispose {
            sosListener.remove()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .background(DarkGrayBlue),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavSlot(
                res = R.drawable.home,
                contentDescription = "Home",
                onClick = { navController.navigate("AdminHomePage_Screen") },
                modifier = Modifier.weight(1f)
            )

            BottomNavSlot(
                res = R.drawable.alert,
                contentDescription = "Alert History",
                onClick = { navController.navigate("AdminSafetyPage_Screen") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(72.dp))

            BottomNavSlot(
                res = R.drawable.safety,
                contentDescription = "Checklist",
                onClick = { navController.navigate("AdminCheckinPage_Screen") },
                modifier = Modifier.weight(1f)
            )

            BottomNavSlot(
                res = R.drawable.profile,
                contentDescription = "Profile",
                onClick = { navController.navigate("AdminProfile_Screen") },
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            modifier = Modifier
                .size(76.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-18).dp)
                .clip(CircleShape)
                .background(Color(0xFFB50000))
                .clickable {
                    navController.navigate("AdminAlertPage_Screen")
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.incoming),
                contentDescription = "Incoming Alerts",
                modifier = Modifier.size(44.dp)
            )
        }
    }

    if (showSosPopup && latestSosAlert != null) {
        AdminGlobalSosPopup(
            alert = latestSosAlert!!,
            onDismiss = {
                showSosPopup = false
            },
            onOpenMap = {
                openMap(
                    link = latestSosAlert?.googleMapsLink.orEmpty(),
                    latitude = latestSosAlert?.latitude,
                    longitude = latestSosAlert?.longitude
                )
            },
            onViewAlert = {
                showSosPopup = false
                navController.navigate("AdminAlertPage_Screen")
            }
        )
    }
}

private fun loadShownSosIds(context: Context): Set<String> {
    val prefs = context.getSharedPreferences(SOS_PREFS, Context.MODE_PRIVATE)
    return prefs.getStringSet(SOS_SHOWN_IDS, emptySet()) ?: emptySet()
}

private fun saveShownSosIds(context: Context, ids: Set<String>) {
    val prefs = context.getSharedPreferences(SOS_PREFS, Context.MODE_PRIVATE)
    prefs.edit().putStringSet(SOS_SHOWN_IDS, ids).apply()
}

@Composable
private fun BottomNavSlot(
    res: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = contentDescription,
            modifier = Modifier.size(34.dp)
        )
    }
}

@Composable
private fun AdminGlobalSosPopup(
    alert: AdminSosPopupData,
    onDismiss: () -> Unit,
    onOpenMap: () -> Unit,
    onViewAlert: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "🚨 New SOS Alert", color = Color.Red)
        },
        text = {
            androidx.compose.foundation.layout.Column {
                Text(text = "User: ${alert.userName}")
                Text(text = "ID Number: ${alert.idNumber}")
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Message: ${alert.message}")

                if (alert.latitude != null && alert.longitude != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Coordinates: ${alert.latitude}, ${alert.longitude}")
                }
            }
        },
        confirmButton = {
            Row {
                TextButton(onClick = onOpenMap) {
                    Text(text = "OPEN MAP")
                }
                TextButton(onClick = onViewAlert) {
                    Text(text = "VIEW ALERT")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "CLOSE")
            }
        }
    )
}