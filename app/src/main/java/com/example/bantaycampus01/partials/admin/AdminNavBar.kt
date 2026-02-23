package com.example.bantaycampus01.partials.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.bantaycampus01.R
import com.example.bantaycampus01.ui.theme.DarkGrayBlue

@Composable
fun AdminNavBar(
    onHome: () -> Unit = {},
    onAlert: () -> Unit = {},
    onIncoming: () -> Unit = {},
    onSafety: () -> Unit = {},
    onProfile: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
                .background(DarkGrayBlue),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavSlot(
                res = R.drawable.home,
                contentDescription = "Home",
                onClick = onHome,
                modifier = Modifier.weight(1f)
            )

            BottomNavSlot(
                res = R.drawable.alert,
                contentDescription = "Alert History",
                onClick = onAlert,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(72.dp))

            BottomNavSlot(
                res = R.drawable.safety,
                contentDescription = "Checklist",
                onClick = onSafety,
                modifier = Modifier.weight(1f)
            )

            BottomNavSlot(
                res = R.drawable.profile,
                contentDescription = "Profile",
                onClick = onProfile,
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            modifier = Modifier
                .size(76.dp)
                .align(Alignment.BottomCenter)
                .offset(y = (-18).dp)
                .clip(CircleShape)
                .background(Color(0xFFB50000))
                .clickable { onIncoming() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.incoming),
                contentDescription = "Incoming Alerts",
                modifier = Modifier.size(44.dp)
            )
        }
    }
}

@Composable
private fun BottomNavSlot(
    res: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(res),
            contentDescription = contentDescription,
            modifier = Modifier.size(34.dp)
        )
    }
}
