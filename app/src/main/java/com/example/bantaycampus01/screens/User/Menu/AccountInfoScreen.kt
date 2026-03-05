package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.User.Menu.PopUps.ChangePasswordDialog

@Composable
fun AccountInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // Table colors to match screenshot
    val tableBg = Color(0xFFC7D2E8)
    val tableBorder = Color(0xFF6F7A8E)
    val tableDivider = Color(0xFF6F7A8E)

    // ✅ Dialog states
    var showChangePassword by remember { mutableStateOf(false) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // ✅ Use Scaffold but DO NOT use bottomBar (UserNavBar is pinned like your other screens)
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
                    .padding(top = 12.dp),
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

                    // ✅ Pencil opens ChangePasswordDialog
                    InfoRowTable(
                        left = "Password",
                        right = "************",
                        showEdit = true,
                        dividerColor = tableDivider,
                        isLast = true,
                        onEditClick = { showChangePassword = true }
                    )
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

        // ✅ Change Password Popup (kept outside Box so it overlays correctly)
        ChangePasswordDialog(
            show = showChangePassword,
            currentPassword = currentPassword,
            onCurrentPasswordChange = { currentPassword = it },
            newPassword = newPassword,
            onNewPasswordChange = { newPassword = it },
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = { confirmPassword = it },
            onConfirm = {
                // UI-only for now
                showChangePassword = false
                currentPassword = ""
                newPassword = ""
                confirmPassword = ""
            },
            onDismiss = {
                showChangePassword = false
                currentPassword = ""
                newPassword = ""
                confirmPassword = ""
            }
        )
    }
}

@Composable
private fun InfoRowTable(
    left: String,
    right: String,
    showEdit: Boolean = false,
    dividerColor: Color,
    isLast: Boolean = false,
    onEditClick: (() -> Unit)? = null
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
                    Text(
                        text = "✎",
                        color = textColor,
                        fontSize = 12.sp,
                        modifier = Modifier.clickable { onEditClick?.invoke() }
                    )
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