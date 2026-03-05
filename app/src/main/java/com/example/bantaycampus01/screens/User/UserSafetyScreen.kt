package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.bantaycampus01.screens.User.Menu.PopUps.MarkSafeDialog

@Composable
fun UserSafetyScreen(
    modifier: Modifier,
    navController: NavController,

    userName: String = "User",

    // Top strip
    currentStatusText: String = "Current Status: ALL SAFE",
    lastCheckinText: String = "Last Student Check-in 10 minutes ago",

    // Advisory
    advisoryTitle: String = "Campus Security Advisory",
    advisoryTime: String = "10:45 AM • Ongoing",
    advisoryHeadline: String = "Attention Students!",
    advisoryBody: String =
        "There is a power outage at the Academic Building that is currently being resolved.\nPlease take caution.",

    // Campus Update
    campusUpdateTitle: String = "CAMPUS UPDATE",
    campusUpdateStatus: String = "SAFE",
    campusUpdateMessage: String = "\"Normal campus operations. No immediate threats reported.\"",
    campusUpdateTime: String = "10:45 AM",

    // Actions / navigation hooks
    onClockClick: () -> Unit = {},
    onViewAdvisoryClick: () -> Unit = {},
    onViewContactsClick: () -> Unit = {},
    onViewMapsClick: () -> Unit = {}
) {
    UserSafetyPageUI(
        modifier = modifier,
        navController = navController,
        userName = userName,

        currentStatusText = currentStatusText,
        lastCheckinText = lastCheckinText,

        advisoryTitle = advisoryTitle,
        advisoryTime = advisoryTime,
        advisoryHeadline = advisoryHeadline,
        advisoryBody = advisoryBody,

        campusUpdateTitle = campusUpdateTitle,
        campusUpdateStatus = campusUpdateStatus,
        campusUpdateMessage = campusUpdateMessage,
        campusUpdateTime = campusUpdateTime,

        onClockClick = onClockClick,
        onViewAdvisoryClick = onViewAdvisoryClick,
        onViewContactsClick = onViewContactsClick,
        onViewMapsClick = onViewMapsClick
    )
}

@Composable
fun UserSafetyPageUI(
    modifier: Modifier,
    navController: NavController,

    userName: String = "User",

    currentStatusText: String,
    lastCheckinText: String,

    advisoryTitle: String,
    advisoryTime: String,
    advisoryHeadline: String,
    advisoryBody: String,

    campusUpdateTitle: String,
    campusUpdateStatus: String,
    campusUpdateMessage: String,
    campusUpdateTime: String,

    onClockClick: () -> Unit,
    onViewAdvisoryClick: () -> Unit,
    onViewContactsClick: () -> Unit,
    onViewMapsClick: () -> Unit
) {
    val advisoryBorder = UserUI.DangerRed
    val cardBg = UserUI.CardBg

    val statusDot = when (campusUpdateStatus.uppercase()) {
        "SAFE" -> UserUI.Green
        "CAUTION" -> Color(0xFFF4B400)
        "RESTRICTED" -> Color(0xFFE53935)
        "RESOLVED" -> UserUI.Green
        else -> UserUI.Green
    }

    val screenScroll = rememberScrollState()

    // ✅ blueprint style: use rememberSaveable like your UserHomePage
    var showMarkedSafeDialog by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UserUI.Bg)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(screenScroll)
        ) {

            // Header (User)
            UserHeader(
                userName = userName,
                onProfileClick = { /* handled by navbar */ }
            )

            // Top status strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(UserUI.DarkBlue)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = currentStatusText,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )
                    Text(
                        text = lastCheckinText,
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontStyle = FontStyle.Italic
                    )
                }

                // ✅ CHANGED: MARK SAFE pill button (matches UserHomePage blueprint)
                Button(
                    onClick = {
                        onClockClick()
                        showMarkedSafeDialog = true
                    },
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.22f)
                    ),
                    modifier = Modifier.size(46.dp)
                ) {
                    Text("✅", fontSize = 24.sp)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Advisory card (bordered)
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .border(2.dp, advisoryBorder, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(12.dp)
                    .clickable { onViewAdvisoryClick() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "👤  $advisoryTitle",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = UserUI.DarkBlue
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = advisoryTime,
                        fontSize = 9.sp,
                        color = UserUI.DarkBlue.copy(alpha = 0.7f)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = advisoryHeadline,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = advisoryBorder
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = advisoryBody,
                    fontSize = 12.sp,
                    color = UserUI.DarkBlue,
                    lineHeight = 14.sp
                )
            }

            Spacer(Modifier.height(14.dp))

            // Campus Update title
            Text(
                text = campusUpdateTitle,
                modifier = Modifier.padding(horizontal = 14.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )

            Spacer(Modifier.height(8.dp))

            // Campus Update card
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(cardBg)
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Status: $campusUpdateStatus",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    Spacer(Modifier.width(6.dp))

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(statusDot)
                    )
                }

                Spacer(Modifier.height(10.dp))

                Text(
                    text = campusUpdateMessage,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = campusUpdateTime,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(Modifier.height(14.dp))

            // Info panels row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                InfoPanel(
                    modifier = Modifier.weight(1f),
                    panelBg = UserUI.DarkBlue,
                    title = "EMERGENCY CONTACTS",
                    titleTextColor = Color.White
                ) {

                    MiniWhiteCard(
                        title = "ADMIN",
                        body = "+63 912 3456 789",
                        onClick = {
                            // TODO: open admin contact
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "GUARD",
                        body = "+63 912 3456 789",
                        onClick = {
                            // TODO: open guard contact
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "CLINIC",
                        body = "+63 912 3456 789",
                        onClick = {
                            // TODO: open clinic contact
                        }
                    )
                }


                InfoPanel(
                    modifier = Modifier.weight(1f),
                    panelBg = UserUI.PaleBlueCard,
                    title = "FIRE EXIT MAPS",
                    titleTextColor = UserUI.DarkBlue
                ) {

                    MiniWhiteCard(
                        title = "ACADEMIC BUILDING",
                        body = "2ND FLOOR",
                        onClick = {
                            // TODO: open 2nd floor map
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "ACADEMIC BUILDING",
                        body = "3RD FLOOR",
                        onClick = {
                            // TODO: open 3rd floor map
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    MiniWhiteCard(
                        title = "ACADEMIC BUILDING",
                        body = "4TH FLOOR",
                        onClick = {
                            // TODO: open 4th floor map
                        }
                    )
                }
            }

            Spacer(Modifier.height(90.dp))
        }

        // Bottom navbar pinned
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(
                modifier = Modifier,
                navController = navController
            )
        }

        // ✅ Mark Safe success dialog
        MarkSafeDialog(
            show = showMarkedSafeDialog,
            onConfirm = { showMarkedSafeDialog = false },
            onDismiss = { showMarkedSafeDialog = false }
        )
    }
}

@Composable
private fun InfoPanel(
    modifier: Modifier = Modifier,
    panelBg: Color,
    title: String,
    titleTextColor: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(panelBg)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = titleTextColor,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(6.dp))
        content()
    }
}

@Composable
private fun MiniWhiteCard(
    title: String,
    body: String,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(
                1.dp,
                UserUI.DarkBlue.copy(alpha = 0.25f),
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = UserUI.DarkBlue,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = body,
            fontSize = 12.sp,
            color = UserUI.DarkBlue,
            textAlign = TextAlign.Center
        )
    }
}