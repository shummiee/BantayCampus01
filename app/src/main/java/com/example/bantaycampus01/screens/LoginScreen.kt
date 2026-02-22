package com.example.bantaycampus01.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.R
import com.example.bantaycampus01.screens.User.MainMenu
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.SubTextLabel
import com.example.bantaycampus01.ui.theme.TextBoxBg
import com.example.bantaycampus01.ui.theme.TextBoxPlaceholder
import com.example.bantaycampus01.ui.theme.TextBoxText
import com.example.bantaycampus01.ui.theme.TextOnDark
import com.example.bantaycampus01.ui.theme.TextOnWhite
import com.example.bantaycampus01.ui.theme.White

@Composable
fun LoginScreen(modifier: Modifier = Modifier){
    val headerColor = DarkGrayBlue
    val fieldBg = TextBoxBg

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    //Hardcoded user
    val demoUserEmail = "user@mcm.edu.ph"
    val demoUserPass = "user123"

    //Hardcoded admin
    val demoAdminEmail = "admin@mcm.edu.ph"
    val demoAdminPass = "admin123"


    Box(modifier = Modifier.fillMaxSize().background(White)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(headerColor),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("BantayCampus", color = White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 180.dp)
                .background(White)
                .padding(horizontal = 30.dp)
                .padding(top = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "LOGIN",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite
            )
            Text(
                text = "Sign in to continue.",
                fontSize = 12.sp,
                color = SubTextLabel
            )

            Spacer(modifier = Modifier.height(18.dp))

            Label("EMAIL")
            Spacer(modifier = Modifier.height(8.dp))
            SoftField(
                value = email,
                onValueChange = { email = it; error = null },
                placeholder = "Email",
                bg = fieldBg,
                keyboardType = KeyboardType.Email,
                isPassword = false,
                showPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            Label("PASSWORD")
            Spacer(modifier = Modifier.height(8.dp))
            SoftField(
                value = password,
                onValueChange = { password = it; error = null },
                placeholder = "Password",
                bg = fieldBg,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                showPassword = showPassword
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showPassword,
                    onCheckedChange = { showPassword = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = headerColor,
                        uncheckedColor = SubTextLabel,
                        checkmarkColor = White
                    )
                )
                Text("Show Password", fontSize = 12.sp, color = SubTextLabel)
            }

            if (error != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        error = "Please enter email and password."
                        return@Button
                    }

                    val e = email.trim()

                    when {
                        e == demoAdminEmail && password == demoAdminPass -> {
                        }

                        e == demoUserEmail && password == demoUserPass -> {
                        }

                        else -> {
                            error = "Invalid email or password."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = headerColor)
            ) {
                Text("Log in", color = TextOnDark, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            TextButton(onClick = { }) {
                Text("Forgot Password?", fontSize = 16.sp, color = SubTextLabel)
            }

            Spacer(modifier = Modifier.height(1.dp))

            TextButton(onClick = { }) {
                Text("Register Here", fontSize = 14.sp, color = SubTextLabel, textDecoration = TextDecoration.Underline)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 62.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo1),
                    contentDescription = "Logo",
                    modifier = Modifier.size(85.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
private fun Label(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 11.sp,
        letterSpacing = 1.sp,
        color = SubTextLabel
    )
}

@Composable
private fun SoftField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    bg: androidx.compose.ui.graphics.Color,
    keyboardType: KeyboardType,
    isPassword: Boolean,
    showPassword: Boolean
) {
    val transformation =
        if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth().height(54.dp),
        placeholder = { Text(placeholder, fontSize = 13.sp, color = TextBoxPlaceholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = transformation,
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = bg,
            unfocusedContainerColor = bg,
            disabledContainerColor = bg,
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            cursorColor = DarkGrayBlue,
            focusedTextColor = TextBoxText,
            unfocusedTextColor = TextBoxText
        )
    )
}