package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.*

@Composable
fun AboutUsScreen(
    onReturn: () -> Unit,
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,
    onAlert: () -> Unit,
    onProfile: () -> Unit
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
                .padding(18.dp)
                .padding(bottom = 96.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("ABOUT US", fontSize = 22.sp, fontWeight = FontWeight.Black, color = UserUI.DarkBlue)

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = "BantayCampus is a mobile-based real-time safety monitoring system designed to enhance the security and well-being of students and staff within the campus.\n\nIt provides a fast and reliable way to report emergencies, receive safety updates, and stay informed about current campus conditions.\n\nYour campus. Your safety. Our priority.",
                    modifier = Modifier.padding(14.dp),
                    color = UserUI.DarkBlue,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    textAlign = TextAlign.Center
                )
            }

            Text("DEVELOPERS", fontSize = 18.sp, fontWeight = FontWeight.Black, color = UserUI.DarkBlue)

            DeveloperCard(name = "Sharmayne Cena", role = "BS Computer Engineering\nFront End & Back End Developer")
            DeveloperCard(name = "Christina Sevilla", role = "BS Computer Engineering\nFront End & Back End Developer")
        }
    }
}

@Composable
private fun DeveloperCard(name: String, role: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(name, fontWeight = FontWeight.Black, color = UserUI.DarkBlue)
            Spacer(Modifier.height(4.dp))
            Text(role, color = UserUI.DarkBlue.copy(alpha = 0.75f), fontSize = 12.sp, textAlign = TextAlign.Center)
        }
    }
}
