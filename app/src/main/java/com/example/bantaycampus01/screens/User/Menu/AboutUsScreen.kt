package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun AboutUsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val cardBg = Color(0xFFE6E6E6)
    val cardBorder = Color(0xFF6F7A8E)
    val textColor = Color(0xFF22304A)

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserTopBar(
                title = "",
                showReturn = true,
                onReturn = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(UserUI.Bg)
        ) {
            Column(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(bottom = 80.dp)
                    .padding(horizontal = 18.dp)
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ABOUT US",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )

                Spacer(Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(18.dp))
                        .background(cardBg)
                        .border(1.dp, cardBorder, RoundedCornerShape(18.dp))
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
                    Text(
                        text = "BantayCampus is a mobile-based\nreal-time safety monitoring system\ndesigned to enhance the security and\nwell-being of students and staff\nwithin the campus. The application\nprovides a fast and reliable way to\nreport emergencies, receive safety\nupdates, and stay informed about\ncurrent campus conditions.",
                        color = textColor,
                        fontSize = 13.sp,
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        text = "Your campus. Your safety. Our priority.",
                        color = textColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "DEVELOPERS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )

                Spacer(Modifier.height(12.dp))

                DeveloperRowCard(
                    name = "Sharmayne Cena",
                    course = "BS Computer Engineering",
                    role = "Full-Stack Developer",
                    avatarRes = R.drawable.cena,
                    cardBg = cardBg,
                    cardBorder = cardBorder,
                    textColor = textColor
                )

                Spacer(Modifier.height(12.dp))

                DeveloperRowCard(
                    name = "Christina Sevilla",
                    course = "BS Computer Engineering",
                    role = "Full-Stack Developer",
                    avatarRes = R.drawable.sevilla,
                    cardBg = cardBg,
                    cardBorder = cardBorder,
                    textColor = textColor
                )

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "PROJECT OVERSEER",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue
                )

                Spacer(Modifier.height(12.dp))

                DeveloperRowCard(
                    name = "Engr. Emmy Grace Requillo",
                    course = "Mapua MCM Professor",
                    role = "Project Adviser",
                    avatarRes = R.drawable.emmy,
                    cardBg = cardBg,
                    cardBorder = cardBorder,
                    textColor = textColor
                )
            }

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                UserNavBar(
                    modifier = Modifier,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun DeveloperRowCard(
    name: String,
    course: String,
    role: String,
    avatarRes: Int,
    cardBg: Color,
    cardBorder: Color,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(cardBg)
            .border(1.dp, cardBorder, RoundedCornerShape(18.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(avatarRes),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = textColor
            )

            Text(
                text = course,
                fontSize = 12.sp,
                color = textColor.copy(alpha = 0.9f)
            )

            Text(
                text = role,
                fontSize = 11.sp,
                color = textColor.copy(alpha = 0.85f)
            )
        }
    }
}