package com.example.bantaycampus01.screens.User.Menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserTopBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.screens.User.Menu.PopUps.ChangePasswordDialog
import com.example.bantaycampus01.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

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
    var isChangingPassword by remember { mutableStateOf(false) }

    var username by remember { mutableStateOf("Loading...") }
    var userRole by remember { mutableStateOf("Loading...") }
    var studentId by remember { mutableStateOf("N/A") }
    var email by remember { mutableStateOf("N/A") }
    var contactNumber by remember { mutableStateOf("N/A") }
    var department by remember { mutableStateOf("N/A") }
    var yearLevel by remember { mutableStateOf("N/A") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
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
                        left = "Student ID",
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
            isLoading = isChangingPassword,
            onConfirm = {
                when {
                    currentPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank() -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Please fill in all password fields.")
                        }
                    }

                    newPassword.length < 6 -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("New password must be at least 6 characters.")
                        }
                    }

                    newPassword != confirmPassword -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("New password and confirm password do not match.")
                        }
                    }

                    currentPassword == newPassword -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("New password must be different from current password.")
                        }
                    }

                    else -> {
                        isChangingPassword = true

                        authViewModel.changePassword(
                            currentPassword = currentPassword,
                            newPassword = newPassword
                        ) { success, message ->
                            isChangingPassword = false

                            scope.launch {
                                snackbarHostState.showSnackbar(message ?: if (success) "Password updated." else "Failed to update password.")
                            }

                            if (success) {
                                showChangePassword = false
                                currentPassword = ""
                                newPassword = ""
                                confirmPassword = ""
                            }
                        }
                    }
                }
            },
            onDismiss = {
                if (!isChangingPassword) {
                    showChangePassword = false
                    currentPassword = ""
                    newPassword = ""
                    confirmPassword = ""
                }
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