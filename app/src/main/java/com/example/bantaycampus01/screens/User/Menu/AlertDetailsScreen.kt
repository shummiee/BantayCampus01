package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.partials.user.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDetailsScreen(
    onReturn: () -> Unit,
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,              // ✅ ADD
    onAlert: () -> Unit,
    onProfile: () -> Unit,
    onAddDetails: () -> Unit
) {
    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserTopBar(
                title = "Alert Details",
                showReturn = true,
                onReturn = onReturn
            )
        },
        bottomBar = {
            UserBottomNavBar(
                onHome = onHome,
                onShield = onShield,
                onSos = onSos,        // ✅ USE
                onAlert = onAlert,
                onProfile = onProfile
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("ALERT DETAILS", fontSize = 24.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(10.dp))
            Text("Flood Alert in Lower Campus", fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Heavy rain has caused flooding. Avoid low areas.")
        }
    }
}

