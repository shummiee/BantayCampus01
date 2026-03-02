package com.example.bantaycampus01.screens.Admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.bantaycampus01.model.CheckInItem
import com.example.bantaycampus01.model.CheckInDateRange
import com.example.bantaycampus01.partials.admin.AdminHeader
import com.example.bantaycampus01.partials.admin.AdminNavBar
import com.example.bantaycampus01.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCheckInPage(
    modifier: Modifier,
    navController: NavController,
    adminName: String = "Admin",
    items: List<CheckInItem> = listOf(
        CheckInItem(
            name = "Juan Dela Cruz",
            rightTime = "Feb 3, 2026 • 10:31 AM",
            statusLabel = "Safe",
            statusColor = Color(0xFF29C65E),
            lastCheckIn = "Feb 3, 2026 • 10:30 AM"
        ),
        CheckInItem(
            name = "Kevin Reyes",
            rightTime = "Feb 3, 2026 • 9:41 AM",
            statusLabel = "Safe",
            statusColor = Color(0xFF29C65E),
            lastCheckIn = "Feb 3, 2026 • 9:40 AM"
        )
    )
) {
    val border = Color(0xFF6F7A8E)
    val cardBg = Color(0xFFCAD6EE)

    var selectedRange by remember { mutableStateOf(CheckInDateRange.TODAY) }
    var filterOpen by remember { mutableStateOf(false) }

    var tempRange by remember { mutableStateOf(selectedRange) }

    val displayedItems = items

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            AdminHeader(adminName = adminName)

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

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(displayedItems) { item ->
                    CheckInCard(
                        item = item,
                        bg = cardBg,
                        border = border
                    )
                }

                item { Spacer(modifier = Modifier.height(90.dp)) }
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AdminNavBar(
                modifier = Modifier,
                navController
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
                    ) { Text("Reset") }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedRange = tempRange
                            filterOpen = false
                        }
                    ) { Text("Apply") }
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
    item: CheckInItem,
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
                text = item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = item.rightTime,
                fontSize = 11.sp,
                color = TextOnWhite.copy(alpha = 0.8f),
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Status: ${item.statusLabel}",
                fontSize = 14.sp,
                color = TextOnWhite
            )
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(RoundedCornerShape(50))
                    .background(item.statusColor)
            )
        }

        Spacer(Modifier.height(2.dp))

        Text(
            text = "Last Check-in: ${item.lastCheckIn}",
            fontSize = 14.sp,
            color = TextOnWhite
        )
    }
}