package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserHeader
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,

    // data
    userName: String = "Sharmayne Cena",
    userRole: String = "Student",

    // actions
    onAccountInfo: () -> Unit = {},
    onNotificationSettings: () -> Unit = {},
    onSchoolContacts: () -> Unit = {},
    onAboutUs: () -> Unit = {},
    onLogout: () -> Unit = {}
) {
    val screenScroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UserUI.Bg)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp) // ✅ like AdminHomePage (keeps navbar visible)
                .verticalScroll(screenScroll)
                .padding(horizontal = 18.dp)
                .padding(top = 16.dp)
        ) {

            // ✅ Uses UserHeader (instead of UserComponents header)
            // On profile page, profile icon can be no-op (already here)
            UserHeader(
                userName = userName,
                onProfileClick = { /* already on profile */ }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Avatar
            Box(
                modifier = Modifier
                    .size(92.dp)
                    .align(Alignment.CenterHorizontally)
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

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = userName,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )

            Text(
                text = userRole,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = UserUI.DarkBlue.copy(alpha = 0.75f)
            )

            Spacer(modifier = Modifier.height(22.dp))

            // Pills
            ProfilePillRow(icon = "👤", text = "ACCOUNT INFO", onClick = onAccountInfo)
            ProfilePillRow(icon = "📞", text = "EMERGENCY CONTACTS", onClick = onSchoolContacts)
            ProfilePillRow(icon = "⚙️", text = "NOTIFICATION SETTINGS", onClick = onNotificationSettings)

            Spacer(modifier = Modifier.height(20.dp))

            // Logout
            Surface(
                onClick = onLogout,
                shape = RoundedCornerShape(50),
                color = UserUI.DarkBlue,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(220.dp)
                    .height(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "LOGOUT",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "About Us",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { onAboutUs() },
                color = UserUI.DarkBlue,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(90.dp))
        }

        // ✅ Bottom pinned navbar (UserNavBar, not AdminNavBar/UserBottomNavBar)
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(
                modifier = Modifier,
                navController = navController
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
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