package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.model.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserAlertScreen(
    modifier: Modifier,
    navController: NavController,
    userName: String = "User",

    // If you already have real alert data, pass it here.
    items: List<UserAlertItem> = listOf(
        UserAlertItem(
            type = AlertCategory.POWER,
            title = "Power Outage",
            timeRight = "Jan 27, 2026 • 8:10 AM",
            location = "Academic Building - 2nd Floor",
            statusLabel = "Acknowledged",
            statusColor = Color(0xFFF4B400), // yellow-ish
            messagePreview = "\"Lights are out in several classrooms...\""
        ),
        UserAlertItem(
            type = AlertCategory.MEDICAL,
            title = "Medical Emergency",
            timeRight = "Jan 27, 2026 • 8:10 AM",
            location = "Canteen Area",
            statusLabel = "Resolved",
            statusColor = UserUI.Green,
            messagePreview = "\"Student fainted and needs medical assistance...\""
        )
    ),

    // Called when user taps View Details
    onViewDetails: (UserAlertItem) -> Unit = {}
) {
    val border = Color(0xFF6F7A8E)
    val cardBg = Color(0xFFCAD6EE)

    var selectedCategory by remember { mutableStateOf(AlertCategory.ALL) }
    var categorySheetOpen by remember { mutableStateOf(false) }
    var filterSheetOpen by remember { mutableStateOf(false) }

    // temp values inside sheets
    var tempCategory by remember { mutableStateOf(selectedCategory) }

    // (Optional) filter placeholders — you can expand later
    val filterOptions = listOf("Newest", "Oldest")
    var selectedSort by remember { mutableStateOf(filterOptions[0]) }
    var tempSort by remember { mutableStateOf(selectedSort) }

    val displayedItems = items
        .filter { selectedCategory == AlertCategory.ALL || it.type == selectedCategory }
        .let { list ->
            when (selectedSort) {
                "Oldest" -> list.reversed()
                else -> list
            }
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UserUI.Bg)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            UserHeader(
                userName = userName,
                onProfileClick = { /* handled by navbar */ }
            )

            // Top pills row (CATEGORY + FILTER) — like screenshot
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                SmallPillButton(
                    text = "CATEGORY",
                    trailing = "▾",
                    onClick = {
                        tempCategory = selectedCategory
                        categorySheetOpen = true
                    }
                )

                Spacer(modifier = Modifier.width(10.dp))

                SmallPillButton(
                    text = "FILTER",
                    trailing = "☰",
                    onClick = {
                        tempSort = selectedSort
                        filterSheetOpen = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 18.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(displayedItems) { item ->
                    UserAlertCard(
                        item = item,
                        bg = cardBg,
                        border = border,
                        onViewDetails = { onViewDetails(item) }
                    )
                }

                item { Spacer(modifier = Modifier.height(90.dp)) }
            }
        }

        // Bottom navbar pinned (like AdminCheckInPage)
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(
                modifier = Modifier,
                navController = navController
            )
        }
    }

    // CATEGORY bottom sheet
    if (categorySheetOpen) {
        ModalBottomSheet(onDismissRequest = { categorySheetOpen = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Category", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(14.dp))

                AlertCategory.entries.forEach { c ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(c.label)
                        RadioButton(
                            selected = (tempCategory == c),
                            onClick = { tempCategory = c }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { tempCategory = AlertCategory.ALL }
                    ) { Text("Reset") }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedCategory = tempCategory
                            categorySheetOpen = false
                        }
                    ) { Text("Apply") }
                }

                Spacer(Modifier.height(22.dp))
            }
        }
    }

    // FILTER bottom sheet (simple sort for now)
    if (filterSheetOpen) {
        ModalBottomSheet(onDismissRequest = { filterSheetOpen = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Filter", fontSize = 18.sp, fontWeight = FontWeight.Black)
                Spacer(Modifier.height(14.dp))

                Text("Sort", fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                filterOptions.forEach { opt ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(opt)
                        RadioButton(
                            selected = (tempSort == opt),
                            onClick = { tempSort = opt }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { tempSort = filterOptions[0] }
                    ) { Text("Reset") }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            selectedSort = tempSort
                            filterSheetOpen = false
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
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
        modifier = Modifier.height(28.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = UserUI.DarkBlue
        )
        Spacer(Modifier.width(6.dp))
        Text(trailing, fontSize = 12.sp, color = UserUI.DarkBlue)
    }
}

@Composable
private fun UserAlertCard(
    item: UserAlertItem,
    bg: Color,
    border: Color,
    onViewDetails: () -> Unit
) {
    val icon = when (item.type) {
        AlertCategory.POWER -> "⚡"
        AlertCategory.MEDICAL -> "🩺"
        AlertCategory.SECURITY -> "⚠️"
        AlertCategory.WEATHER -> "🌧️"
        AlertCategory.ALL -> "🔔"
    }

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
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Text(icon, fontSize = 16.sp)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )
            }

            Text(
                text = item.timeRight,
                fontSize = 10.sp,
                color = UserUI.DarkBlue.copy(alpha = 0.75f),
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(Modifier.height(6.dp))

        Text(
            text = "• ${item.location}",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = UserUI.DarkBlue
        )

        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Status: ${item.statusLabel}",
                fontSize = 12.sp,
                color = UserUI.DarkBlue,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.width(6.dp))

            Box(
                modifier = Modifier
                    .size(9.dp)
                    .clip(CircleShape)
                    .background(item.statusColor)
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = item.messagePreview,
            fontSize = 12.sp,
            color = UserUI.DarkBlue.copy(alpha = 0.9f)
        )

        Spacer(Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "View Details →",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = UserUI.DarkBlue.copy(alpha = 0.75f),
                modifier = Modifier.clickable { onViewDetails() }
            )
        }
    }
}