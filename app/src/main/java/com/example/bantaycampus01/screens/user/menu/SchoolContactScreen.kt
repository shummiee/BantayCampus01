package com.example.bantaycampus01.screens.user.menu

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI

@Composable
fun SchoolContactsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current

    fun openDialer(phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to open phone app.", Toast.LENGTH_SHORT).show()
        }
    }

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
                        ContactRow(
                            name = "School Clinic",
                            number = "+63 912 345 6789",
                            onClick = { openDialer("+639123456789") }
                        )
                        DividerLine()
                        ContactRow(
                            name = "Guard House",
                            number = "+63 917 123 4567",
                            onClick = { openDialer("+639171234567") }
                        )
                        DividerLine()
                        ContactRow(
                            name = "Admin",
                            number = "+63 918 987 6543",
                            onClick = { openDialer("+639189876543") }
                        )
                    }
                }
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
private fun ContactRow(
    name: String,
    number: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
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