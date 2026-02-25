package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.foundation.clickable
import com.example.bantaycampus01.partials.user.*

@Composable
fun ProfileScreen(
    onHome: () -> Unit = {},
    onAlert: () -> Unit = {},
    onShield: () -> Unit = {},
    onProfile: () -> Unit = {},
    onAccountInfo: () -> Unit = {},
    onNotificationSettings: () -> Unit = {},
    onSchoolContacts: () -> Unit = {},
    onAddDetails: () -> Unit = {},
    onAboutUs: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    Scaffold(
        containerColor = UserUI.Bg,
        bottomBar = { UserBottomNavBar(onHome, onShield, onAddDetails, onAlert, onProfile) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 16.dp, bottom = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Avatar
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
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Sharmayne Cena",
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )
            Text(
                text = "Student",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = UserUI.DarkBlue.copy(alpha = 0.75f)
            )
            /*
            ProfilePillButton("ACCOUNT INFO", onAccountInfo)
            ProfilePillButton("EMERGENCY CONTACTS", onSchoolContacts)
            ProfilePillButton("NOTIFICATION SETTINGS", onNotificationSettings)
            ProfilePillButton("ABOUT US", onAboutUs)*/

            Spacer(Modifier.height(18.dp))

            ProfilePillRow(icon = "👤", text = "ACCOUNT INFO", onClick = onAccountInfo)
            ProfilePillRow(icon = "📞", text = "EMERGENCY CONTACTS", onClick = onSchoolContacts)
            ProfilePillRow(icon = "⚙️", text = "NOTIFICATION SETTINGS", onClick = onNotificationSettings)

            Spacer(Modifier.weight(1f))

            // Logout (smaller like wireframe)
            Box(
                modifier = Modifier
                    .width(220.dp)
                    .height(44.dp)
                    .background(UserUI.DarkBlue, RoundedCornerShape(50))
                    .clickable { onLogout() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "LOGOUT",
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "About Us",
                color = UserUI.DarkBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onAboutUs() }
            )
        }
    }
}

@Composable
private fun ProfilePillRow(
    icon: String,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(UserUI.DarkBlue, RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, fontSize = 16.sp, color = Color.White)
        Spacer(Modifier.width(10.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
        )
    }

    Spacer(Modifier.height(12.dp))
}
