package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.ui.theme.*
import com.example.bantaycampus01.partials.user.*
import com.example.bantaycampus01.R

@Composable
fun UserHomePage(
    onSendReport: () -> Unit,
    onReportHistory: () -> Unit,
    onReportStatus: () -> Unit,
    onNotifications: () -> Unit,
    onProfile: () -> Unit,
    onSos: () -> Unit // ✅ ADDED
) {

    val scroll = rememberScrollState()

    Scaffold(
        containerColor = UserUI.Bg,
        bottomBar = {
            UserBottomNavBar(
                onHome = { /* already here */ },
                onShield = onReportStatus,     // shield → safety/report status
                onSos = onSos,                 // ✅ SOS popup route
                onAlert = onNotifications,
                onProfile = onProfile
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()

                .verticalScroll(scroll)
                .padding(bottom = 96.dp)

        ) {

            UserHeaderBar(onProfile = onProfile)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onNotifications) {
                    Icon(
                        imageVector = Icons.Outlined.NotificationsNone,
                        contentDescription = "Notifications",
                        tint = UserUI.DarkBlue,
                        modifier = Modifier.size(24.dp)

                    )
                }
            }

            // CAMPUS RISK LEVEL section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UserUI.CardBg)
                    .padding(horizontal = 18.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "CAMPUS RISK LEVEL",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = TextOnDark
                )

                Spacer(Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Current Campus Status: SAFE",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(9.dp)
                            .clip(CircleShape)
                            .background(UserUI.Green)
                    )
                }

                Text(
                    "Last Updated: 10:45 am by Admin",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(10.dp))

                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "SAFETY UPDATE",
                                    fontWeight = FontWeight.Black,
                                    color = UserUI.DarkBlue,
                                    fontSize = 20.sp
                                )
                                Text(
                                    "9:23 AM",
                                    color = UserUI.DarkBlue.copy(alpha = 0.75f),
                                    fontSize = 11.sp
                                )
                            }

                            // MARK SAFE pill (UI only)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(22.dp))
                                    .background(UserUI.DarkBlue)
                                    .padding(horizontal = 12.dp, vertical = 7.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "MARK SAFE",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(14.dp)
                                            .clip(CircleShape)
                                            .background(UserUI.Green),
                                        contentAlignment = Alignment.Center
                                    ) {

                                        Text("✓", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Black)

                                        Text(
                                            "✓",
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Black
                                        )

                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(UserUI.LightCard)
                                .padding(12.dp)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Power outage at Academic Building",
                                    fontWeight = FontWeight.Black,
                                    color = UserUI.DarkBlue,
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "Status: RESOLVED",
                                        fontWeight = FontWeight.Bold,
                                        color = UserUI.DarkBlue,
                                        fontSize = 16.sp
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(9.dp)
                                            .clip(CircleShape)
                                            .background(UserUI.Green)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // Bottom actions + campus advisory row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ActionPillButton(
                        title = "SEND REPORT",
                        bg = UserUI.DangerRed,
                        fg = Color.White,
                        icon = R.drawable.incoming,
                        onClick = onSendReport
                    )
                    ActionPillButton(
                        title = "REPORT\nHISTORY",
                        bg = UserUI.PaleBlueCard,
                        fg = UserUI.DarkBlue,
                        icon = R.drawable.cases,
                        onClick = onReportHistory
                    )
                    ActionPillButton(
                        title = "REPORT\nSTATUS",
                        bg = UserUI.PaleBlueCard,
                        fg = UserUI.DarkBlue,
                        icon = R.drawable.risk,
                        onClick = onReportStatus
                    )
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = UserUI.CardBg)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "CAMPUS\nADVISORY",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color.White)
                                .padding(10.dp)
                        ) {
                            Text(
                                "Due to heavy rain, some walkways may be slippery. Please take extra caution when moving around campus.",
                                color = UserUI.DarkBlue,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                "“Committed to your safety on campus.”",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                textAlign = TextAlign.Center,
                color = UserUI.DarkBlue.copy(alpha = 0.65f),
                fontSize = 12.sp
            )

            // ✅ extra padding at end (safe)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    title: String,
    value: String,
    bgColor: Color,
    valueFont: TextUnit = 34.sp
) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                title,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                value,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = valueFont,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ActionPillButton(
    title: String,
    bg: Color,
    fg: Color,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
                color = fg,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
