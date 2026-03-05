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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserBottomNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun AccountInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // Table colors to match screenshot
    val tableBg = Color(0xFFC7D2E8)              // slightly stronger pale blue
    val tableBorder = Color(0xFF6F7A8E)          // gray-blue border like screenshot
    val tableDivider = Color(0xFF6F7A8E)

    Scaffold(
        containerColor = UserUI.Bg,
        topBar = {
            UserTopBar(
                title = "",
                showReturn = true,
                onReturn = { navController.popBackStack() }
            )
        },
        bottomBar = {
            UserBottomNavBar(
                onHome = { navController.navigate("UserHomePage_Screen") },
                onShield = { navController.navigate("UserSafety_Screen") },
                onSos = { /* TODO: navController.navigate("UserSos_Screen") */ },
                onAlert = { navController.navigate("UserAlert_Screen") },
                onProfile = { navController.navigate("UserProfile_Screen") }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .padding(top = 12.dp, bottom = 110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(102.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(84.dp)
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
                Spacer(Modifier.width(8.dp))
                Text("✎", color = UserUI.DarkBlue, fontSize = 14.sp) // UI only
            }

            Text(
                text = "Student",
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                color = UserUI.DarkBlue.copy(alpha = 0.75f)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Basic Information",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = UserUI.DarkBlue
            )

            Spacer(Modifier.height(10.dp))

            // ✅ Bordered table (matches screenshot)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(2.dp))
                    .background(tableBg)
                    .border(1.dp, tableBorder, RoundedCornerShape(2.dp))
            ) {
                InfoRowTable(
                    left = "Student ID",
                    right = "2023150518",
                    showEdit = false,
                    dividerColor = tableDivider
                )
                InfoRowTable("E-mail Address", "saCena@mmc.edu.ph", dividerColor = tableDivider)
                InfoRowTable("Contact Number", "0939 815 4694", dividerColor = tableDivider)
                InfoRowTable("Department/Course", "CEA - BS Computer Engineering", dividerColor = tableDivider)
                InfoRowTable("Year Level", "3rd Year", dividerColor = tableDivider)
                InfoRowTable(
                    left = "Password",
                    right = "************",
                    showEdit = true,
                    dividerColor = tableDivider,
                    isLast = true
                )
            }
        }
    }
}

@Composable
private fun InfoRowTable(
    left: String,
    right: String,
    showEdit: Boolean = false,
    dividerColor: Color,
    isLast: Boolean = false
) {
    val textColor = Color(0xFF22304A)

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = left,
                modifier = Modifier.weight(1f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = textColor
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = right,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                if (showEdit) {
                    Spacer(Modifier.width(8.dp))
                    Text("✎", color = textColor, fontSize = 12.sp)
                }
            }
        }

        if (!isLast) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(dividerColor)
            )
        }
    }
}