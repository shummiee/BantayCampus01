package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.*


@Composable
fun ReportStatusScreen(
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,
    onAlert: () -> Unit,
    onProfile: () -> Unit,
    onReturn: (() -> Unit)? = null
) {
    var selectedFloor by remember { mutableStateOf("2ND FLOOR") }

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserHeaderBar(
                onProfile = onProfile
            )
        }
        ,
        bottomBar = {
            UserBottomNavBar(
                onHome = onHome,
                onShield = onShield,
                onSos = onSos,
                onAlert = onAlert,
                onProfile = onProfile
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.LightCard)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "ACADEMIC BUILDING",
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(10.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(Modifier.weight(1f)) {
                            FloorChip("2ND FLOOR", selectedFloor) { selectedFloor = "2ND FLOOR" }
                        }
                        Box(Modifier.weight(1f)) {
                            FloorChip("3RD FLOOR", selectedFloor) { selectedFloor = "3RD FLOOR" }
                        }
                        Box(Modifier.weight(1f)) {
                            FloorChip("4TH FLOOR", selectedFloor) { selectedFloor = "4TH FLOOR" }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.LightCard)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "FIRE EXIT MAP PLACEHOLDER\n\nAcademic Building – $selectedFloor\n\n(Insert image later)",
                        textAlign = TextAlign.Center,
                        color = UserUI.DarkBlue,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp
                    )
                }
            }

            Text(
                text = "Tip: Tap the floor buttons above to switch maps.",
                color = UserUI.DarkBlue.copy(alpha = 0.75f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun FloorChip(
    label: String,
    selected: String,
    onClick: () -> Unit
) {
    val isSelected = label == selected
    val bg = if (isSelected) UserUI.DarkBlue else UserUI.PaleBlueCard
    val fg = if (isSelected) UserUI.Bg else UserUI.DarkBlue

    Box(
        modifier = Modifier
            .height(38.dp)
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .background(bg, RoundedCornerShape(50))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = fg,
            fontWeight = FontWeight.Black,
            fontSize = 12.sp
        )
    }
}
