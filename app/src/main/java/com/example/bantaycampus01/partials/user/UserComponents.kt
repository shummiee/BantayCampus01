package com.example.bantaycampus01.partials.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.R

object UserUI {
    val DarkBlue = Color(0xFF1F2F45)
    val Bg = Color(0xFFF6F7FB)
    val CardBg = Color(0xFF2B3C54)
    val LightCard = Color(0xFFCFD6E3)
    val DangerRed = Color(0xFFB01717)
    val SoftGray = Color(0xFFDFE6F1)
    val PaleBlueCard = Color(0xFFBFC9DD)
    val Green = Color(0xFF2ECC71)
}

/**
 * Wireframe header:
 * LEFT: avatar + Hi, User! + View Profile
 * RIGHT: BantayCampus + logo1 VERY CLOSE
 */
@Composable
fun UserHeaderBar(
    onProfile: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth().background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.clickable { onProfile() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(UserUI.LightCard),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.profile),
                        contentDescription = "Profile",
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        "Hi, User!",
                        fontWeight = FontWeight.Bold,
                        color = UserUI.DarkBlue,
                        fontSize = 14.sp
                    )
                    Text(
                        "View Profile",
                        color = UserUI.DarkBlue.copy(alpha = 0.65f),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "BantayCampus",
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue,
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(3.dp)) // tighter like wireframe
                Image(
                    painter = painterResource(R.drawable.logo1), // ✅ use logo1.png
                    contentDescription = "Logo",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        HorizontalDivider(color = Color(0xFFE5E5E5), thickness = 1.dp)
    }
}

/**
 * Use ONLY for pages that need a simple title + optional RETURN.
 * (If the wireframe page shows the Hi User header, use UserHeaderBar instead)
 */
@Composable
fun UserTopBar(
    title: String,
    showReturn: Boolean = false,
    onReturn: (() -> Unit)? = null
) {
    Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showReturn) {
                Text(
                    "← RETURN",
                    fontWeight = FontWeight.Black,
                    color = UserUI.DarkBlue,
                    modifier = Modifier.clickable { onReturn?.invoke() }
                )
                Spacer(Modifier.width(12.dp))
            }

            Text(
                title,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue,
                fontSize = 20.sp
            )

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier.size(26.dp)
            )
        }
        HorizontalDivider(color = Color(0xFFE5E5E5), thickness = 1.dp)
    }
}

/**
 * Bottom nav: Home | Shield | (SOS) | Alert | Profile
 * NOTE: SOS should open SOS popup flow (we’ll route it)
 */
@Composable
fun UserBottomNavBar(
    onHome: () -> Unit,
    onShield: () -> Unit,
    onSos: () -> Unit,
    onAlert: () -> Unit,
    onProfile: () -> Unit
) {
    val barBg = UserUI.DarkBlue
    val iconColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(86.dp)
            .background(barBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 26.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomIcon(onClick = onHome) {
                Image(
                    painter = painterResource(R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }

            BottomIcon(onClick = onShield) {
                Image(
                    painter = painterResource(R.drawable.shield),
                    contentDescription = "Shield",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }

            Spacer(Modifier.width(84.dp))

            BottomIcon(onClick = onAlert) {
                Image(
                    painter = painterResource(R.drawable.alert),
                    contentDescription = "Alerts",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }

            BottomIcon(onClick = onProfile) {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }
        }

        Box(
            modifier = Modifier
                .size(78.dp)
                .align(Alignment.TopCenter)
                .offset(y = (-24).dp)
                .clip(CircleShape)
                .background(UserUI.DangerRed)
                .clickable { onSos() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.sos),
                    contentDescription = "SOS",
                    modifier = Modifier.size(50.dp),
                    colorFilter = ColorFilter.tint(iconColor)
                )
            }
        }
    }
}

@Composable
private fun BottomIcon(
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}
