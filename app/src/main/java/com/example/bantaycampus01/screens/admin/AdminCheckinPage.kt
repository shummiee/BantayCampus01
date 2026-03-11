package com.example.bantaycampus01.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.model.CheckInDateRange
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.TextOnWhite
import com.example.bantaycampus01.ui.theme.White
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class AdminCheckInRecord(
    val checkInId: String = "",
    val userId: String = "",
    val userName: String = "",
    val idNumber: String = "",
    val status: String = "",
    val location: String = "",
    val timestamp: Long = 0L
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCheckInPage(
    modifier: Modifier,
    navController: NavController,
    adminName: String = "Admin"
) {
    val border = Color(0xFF6F7A8E)
    val cardBg = Color(0xFFCAD6EE)
    val db = remember { FirebaseFirestore.getInstance() }

    val allCheckIns = remember { mutableStateListOf<AdminCheckInRecord>() }

    var selectedRange by remember { mutableStateOf(CheckInDateRange.TODAY) }
    var filterOpen by remember { mutableStateOf(false) }
    var tempRange by remember { mutableStateOf(selectedRange) }

    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        val listener =
            db.collection("users")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        errorMessage = "Failed to load check-in history: ${error.message}"
                        isLoading = false
                        return@addSnapshotListener
                    }

                    val records = snapshot?.documents?.mapNotNull { doc ->
                        val safetyStatus = doc.getString("safetyStatus") ?: ""

                        if (safetyStatus.uppercase() != "SAFE") {
                            return@mapNotNull null
                        }

                        AdminCheckInRecord(
                            checkInId = doc.id,
                            userId = doc.getString("accountId") ?: doc.id,
                            userName = doc.getString("name") ?: "Unknown User",
                            idNumber = doc.getString("idNumber") ?: "No ID number",
                            status = safetyStatus,
                            location = doc.getString("department") ?: "No location provided",
                            timestamp = doc.getLong("lastCheckIn") ?: 0L
                        )
                    } ?: emptyList()

                    allCheckIns.clear()
                    allCheckIns.addAll(records.sortedByDescending { it.timestamp })

                    errorMessage = null
                    isLoading = false
                }

        onDispose {
            listener.remove()
        }
    }

    val displayedItems = allCheckIns.filter { record ->
        when (selectedRange) {
            CheckInDateRange.TODAY -> isToday(record.timestamp)
            CheckInDateRange.LAST_7 -> isLast7Days(record.timestamp)
            CheckInDateRange.LAST_30 -> isLast30Days(record.timestamp)
            CheckInDateRange.CUSTOM -> true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            AdminHeader()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "CHECK-IN HISTORY",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnWhite
                )

                Spacer(modifier = Modifier.weight(1f))

                SmallPillButton(
                    text = "FILTER",
                    trailing = "☰",
                    border = DarkGrayBlue,
                    onClick = {
                        tempRange = selectedRange
                        filterOpen = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = DarkGrayBlue)
                    }
                }

                errorMessage != null -> {
                    Text(
                        text = errorMessage ?: "Unknown error",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )
                }

                displayedItems.isEmpty() -> {
                    Text(
                        text = "No safe check-ins found.",
                        fontSize = 15.sp,
                        color = TextOnWhite,
                        modifier = Modifier.padding(horizontal = 18.dp)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(displayedItems, key = { it.checkInId }) { item ->
                            CheckInCard(
                                item = item,
                                bg = cardBg,
                                border = border
                            )
                        }

                        item { Spacer(modifier = Modifier.height(90.dp)) }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AdminNavBar(
                modifier = Modifier,
                navController = navController
            )
        }
    }

    if (filterOpen) {
        ModalBottomSheet(onDismissRequest = { filterOpen = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Filter", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(14.dp))

                Text("Date / Time", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                CheckInDateRange.entries.forEach { r ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(r.label)
                        RadioButton(
                            selected = (tempRange == r),
                            onClick = { tempRange = r }
                        )
                    }
                }

                if (tempRange == CheckInDateRange.CUSTOM) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Custom range date picker can be added next.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            tempRange = CheckInDateRange.TODAY
                        }
                    ) {
                        Text("Reset")
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedRange = tempRange
                            filterOpen = false
                        }
                    ) {
                        Text("Apply")
                    }
                }

                Spacer(Modifier.height(22.dp))
            }
        }
    }
}

@Composable
private fun SmallPillButton(
    text: String,
    trailing: String,
    onClick: () -> Unit,
    border: Color
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
        modifier = Modifier.height(26.dp)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextOnWhite)
        Spacer(Modifier.width(6.dp))
        Text(trailing, fontSize = 12.sp, color = TextOnWhite)
    }
}

@Composable
private fun CheckInCard(
    item: AdminCheckInRecord,
    bg: Color,
    border: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, border, RoundedCornerShape(14.dp))
            .clip(RoundedCornerShape(14.dp))
            .background(bg)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = item.userName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = formatDateTime(item.timestamp),
                fontSize = 11.sp,
                color = TextOnWhite.copy(alpha = 0.8f),
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = "ID Number: ${item.idNumber}",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Status: ${item.status}",
                fontSize = 14.sp,
                color = TextOnWhite
            )
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF29C65E))
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Location: ${item.location}",
            fontSize = 14.sp,
            color = TextOnWhite
        )

        Text(
            text = "Last Check-in: ${formatDateTime(item.timestamp)}",
            fontSize = 14.sp,
            color = TextOnWhite
        )
    }
}

private fun formatDateTime(timestamp: Long): String {
    if (timestamp <= 0L) return "Unknown time"
    val formatter = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault())
    return formatter.format(Date(timestamp))
}

private fun isToday(timestamp: Long): Boolean {
    if (timestamp <= 0L) return false

    val now = Calendar.getInstance()
    val target = Calendar.getInstance().apply { timeInMillis = timestamp }

    return now.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
            now.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
}

private fun isLast7Days(timestamp: Long): Boolean {
    if (timestamp <= 0L) return false
    val now = System.currentTimeMillis()
    val sevenDaysAgo = now - (7L * 24 * 60 * 60 * 1000)
    return timestamp >= sevenDaysAgo
}

private fun isLast30Days(timestamp: Long): Boolean {
    if (timestamp <= 0L) return false
    val now = System.currentTimeMillis()
    val thirtyDaysAgo = now - (30L * 24 * 60 * 60 * 1000)
    return timestamp >= thirtyDaysAgo
}