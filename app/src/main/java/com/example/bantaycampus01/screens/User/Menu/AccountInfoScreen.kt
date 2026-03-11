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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.User.Menu.PopUps.ChangePasswordDialog
import com.example.bantaycampus01.viewmodel.AuthViewModel

@Composable
fun AccountInfoScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val tableBg = Color(0xFFC7D2E8)
    val tableBorder = Color(0xFF6F7A8E)
    val tableDivider = Color(0xFF6F7A8E)

    var showChangePassword by remember { mutableStateOf(false) }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // User info states
    var username by remember { mutableStateOf("") }
    var userRole by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        authViewModel.getUserProfile { name, userEmail, userContactNumber, idNumber, userDepartment, role ->
            username = if (!name.isNullOrBlank()) name else "User"
            userRole = if (!role.isNullOrBlank()) role else "User"
            studentId = if (!idNumber.isNullOrBlank()) idNumber else "N/A"
            email = if (!userEmail.isNullOrBlank()) userEmail else "N/A"
            contactNumber = if (!userContactNumber.isNullOrBlank()) userContactNumber else "N/A"
            department = if (!userDepartment.isNullOrBlank()) userDepartment else "N/A"
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
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        text = username,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        color = UserUI.DarkBlue
                    )
                }

                Text(
                    text = userRole,
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(2.dp))
                        .background(tableBg)
                        .border(1.dp, tableBorder, RoundedCornerShape(2.dp))
                ) {
                    InfoRowTable(
                        left = "ID Number",
                        right = studentId,
                        showEdit = false,
                        dividerColor = tableDivider
                    )
                    InfoRowTable(
                        left = "E-mail Address",
                        right = email,
                        dividerColor = tableDivider
                    )
                    InfoRowTable(
                        left = "Contact Number",
                        right = contactNumber,
                        dividerColor = tableDivider
                    )
                    InfoRowTable(
                        left = "Department",
                        right = department,
                        dividerColor = tableDivider
                    )
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

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                UserNavBar(
                    modifier = Modifier,
                    navController = navController
                )
            }
        }

        ChangePasswordDialog(
            show = showChangePassword,
            currentPassword = currentPassword,
            onCurrentPasswordChange = { currentPassword = it },
            newPassword = newPassword,
            onNewPasswordChange = { newPassword = it },
            confirmPassword = confirmPassword,
            onConfirmPasswordChange = { confirmPassword = it },
            onConfirm = {
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