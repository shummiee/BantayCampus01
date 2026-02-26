package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.*

@Composable
fun AccountInfoScreen(
    onReturn: () -> Unit,
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,
    onAlert: () -> Unit,
    onProfile: () -> Unit,
    onAddDetails: () -> Unit // not used, keep for compatibility
) {
    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserTopBar(
                title = "",
                showReturn = true,
                onReturn = onReturn
            )
        },
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
                .padding(horizontal = 18.dp)
                .padding(top = 10.dp, bottom = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar + Name
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Sharmayne Cena",
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp,
                    color = UserUI.DarkBlue
                )
                Spacer(Modifier.width(6.dp))
                Text("✎", color = UserUI.DarkBlue, fontSize = 14.sp) // UI only
            }

            Text(
                text = "Student",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = UserUI.DarkBlue.copy(alpha = 0.75f)
            )

            Spacer(Modifier.height(12.dp))

            // Table title
            Text(
                text = "Basic Information",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = UserUI.DarkBlue
            )

            Spacer(Modifier.height(8.dp))

            // Info table
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = UserUI.PaleBlueCard),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column {
                    InfoRow("Student ID", "2023-150518")
                    DividerLine()
                    InfoRow("E-mail Address", "saCena@mmc.edu.ph")
                    DividerLine()
                    InfoRow("Contact Number", "0939 815 4804")
                    DividerLine()
                    InfoRow("Department/Course", "CEA - BS Computer Engineering")
                    DividerLine()
                    InfoRow("Year Level", "3rd Year")
                    DividerLine()
                    InfoRow("Password", "••••••••••", showEdit = true)
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    left: String,
    right: String,
    showEdit: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = left,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = UserUI.DarkBlue
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = right,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = UserUI.DarkBlue
            )
            if (showEdit) {
                Spacer(Modifier.width(6.dp))
                Text("✎", color = UserUI.DarkBlue, fontSize = 12.sp) // UI only
            }
        }
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(UserUI.DarkBlue.copy(alpha = 0.15f))
    )
}
