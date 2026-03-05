package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun SchoolContactsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // ✅ Do NOT use bottomBar; pin UserNavBar like UserProfileScreen
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
                    .padding(bottom = 80.dp) // ✅ space for UserNavBar
                    .padding(horizontal = 18.dp)
                    .padding(top = 10.dp)
            ) {
                Text(
                    text = "School’s Emergency Contacts",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = UserUI.DarkBlue
                )

                Spacer(Modifier.height(8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = UserUI.PaleBlueCard),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column {
                        ContactRow("School Clinic", "+63 912 345 6789")
                        DividerLine()
                        ContactRow("Guard House", "+63 912 345 6789")
                        DividerLine()
                        ContactRow("Admin", "+63 912 345 6789")
                    }
                }
            }

            // ✅ Bottom pinned navbar (UserNavBar.kt)
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
private fun ContactRow(name: String, number: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = name,
            modifier = Modifier.weight(1f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = UserUI.DarkBlue
        )
        Text(
            text = number,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = UserUI.DarkBlue
        )
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(UserUI.DarkBlue.copy(alpha = 0.15f))
    )
}