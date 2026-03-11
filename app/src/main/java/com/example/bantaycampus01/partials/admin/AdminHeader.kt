package com.example.bantaycampus01.partials.admin

import androidx.compose.runtime.getValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bantaycampus01.R
import com.example.bantaycampus01.ui.theme.*
import com.example.bantaycampus01.viewmodel.AuthViewModel

@Composable
fun AdminHeader(
) {
    val authViewModel = viewModel<AuthViewModel>()

    var username by remember { mutableStateOf("User") }

    LaunchedEffect(Unit) {
        authViewModel.getUserName { name ->
            username = name ?: "User"
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 25.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                tint = DarkGrayBlue,
                modifier = Modifier.size(44.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.clickable {  }
            ) {
                Text(
                    text = "Hi, $username!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextOnWhite
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "BantayCampus",
                fontSize = 14.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite
            )

            Spacer(modifier = Modifier.width(0.5.dp))

            Image(
                painter = painterResource(R.drawable.logo1),
                contentDescription = "Logo",
                modifier = Modifier.size(44.dp)
            )
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = SubTextLabel
        )
    }
}
