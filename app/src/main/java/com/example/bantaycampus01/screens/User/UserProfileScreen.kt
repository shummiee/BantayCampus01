package com.example.bantaycampus01.screens.User

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bantaycampus01.AppUtil
import com.example.bantaycampus01.R
import com.example.bantaycampus01.partials.user.UserNavBar
import com.example.bantaycampus01.partials.user.UserUI
import com.example.bantaycampus01.viewmodel.AuthViewModel

@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    var context = LocalContext.current
    val screenScroll = rememberScrollState()

    val authViewModel = viewModel<AuthViewModel>()

    var username by remember { mutableStateOf("User") }
    var userrole by remember { mutableStateOf("User") }

    LaunchedEffect(Unit) {
        authViewModel.getUserProfile { name, email, contactNumber, idNumber, department, role  ->
            username = name ?: "User"
            userrole = role ?: "User"
        }
    }

    BackHandler(enabled = true){
        AppUtil.showToast(context, "You cannot go back to previous page")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(UserUI.Bg)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .padding(horizontal = 18.dp)
                .padding(top = 16.dp)
                .verticalScroll(screenScroll),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar (top-center)
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(88.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = username,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = UserUI.DarkBlue
            )

            Text(
                text = userrole,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Medium,
                color = UserUI.DarkBlue.copy(alpha = 0.75f)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Pill buttons (match screenshot)
            ProfilePillRow(
                icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null, tint = Color.White) },
                text = "ACCOUNT INFO",
                onClick = { navController.navigate("AccountInfo_Screen") }
            )

            ProfilePillRow(
                icon = { Icon(Icons.Filled.Call, contentDescription = null, tint = Color.White) },
                text = "EMERGENCY CONTACTS", // (kept as screenshot spelling)
                onClick = { navController.navigate("SchoolContact_Screen") }
            )
            // Big spacing before logout (like screenshot)
            Spacer(modifier = Modifier.height(180.dp))

            // Logout pill (centered)
            Surface(
                onClick = {
                    authViewModel.logout { success, error ->
                        if (success) {
                            navController.navigate("Login_Screen"){
                                popUpTo(0) { inclusive = true }
                                launchSingleTop = true  }
                        } else {
                            AppUtil.showToast(context, error?: "Logout Failed")
                        }
                    }},
                shape = RoundedCornerShape(50),
                color = UserUI.DarkBlue,
                tonalElevation = 0.dp,
                modifier = Modifier
                    .width(190.dp)
                    .height(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "LOGOUT",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "About Us",
                modifier = Modifier
                    .clickable { navController.navigate("AboutUs_Screen") },
                color = UserUI.DarkBlue,
                fontWeight = FontWeight.Black,
                fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.Underline
            )
        }

        // Navbar
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            UserNavBar(
                modifier = Modifier,
                navController = navController
            )
        }
    }
}

@Composable
private fun ProfilePillRow(
    icon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(50))
            .background(UserUI.DarkBlue)
            .clickable { onClick() }
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
            icon()
        }
        Spacer(Modifier.width(12.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Black
        )
    }

    Spacer(Modifier.height(14.dp))
}