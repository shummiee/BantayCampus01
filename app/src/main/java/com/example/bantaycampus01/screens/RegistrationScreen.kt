package com.example.bantaycampus01.screens

import android.app.DatePickerDialog
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bantaycampus01.AppUtil
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.SubTextLabel
import com.example.bantaycampus01.ui.theme.TextBoxBg
import com.example.bantaycampus01.ui.theme.TextBoxPlaceholder
import com.example.bantaycampus01.ui.theme.TextBoxText
import com.example.bantaycampus01.ui.theme.TextOnDark
import com.example.bantaycampus01.ui.theme.TextOnWhite
import com.example.bantaycampus01.ui.theme.White
import com.example.bantaycampus01.viewmodel.AuthViewModel
import java.util.Calendar

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val headerColor = DarkGrayBlue
    val fieldBg = TextBoxBg

    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var idNumber by remember { mutableStateOf("") }
    var department by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var dob by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }

    fun openDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            context,
            { _, y, m, d ->
                dob = "%02d/%02d/%04d".format(m + 1, d, y) // MM/DD/YYYY
            },
            year, month, day
        ).show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(headerColor)
        ) {
            IconButton(
                onClick = { navController.navigate("Login_Screen") },
                modifier = Modifier
                    .padding(start = 12.dp, top = 16.dp)
                    .size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
                .background(White)
                .verticalScroll(scrollState)
                .padding(horizontal = 30.dp)
                .padding(top = 28.dp)
                .padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CREATE NEW ACCOUNT",
                fontSize = 30.sp,
                fontWeight = FontWeight.Black,
                color = TextOnWhite,
                lineHeight = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Name
            SoftField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Name",
                bg = fieldBg,
                keyboardType = KeyboardType.Text,
                isPassword = false,
                showPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Email
            SoftField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                bg = fieldBg,
                keyboardType = KeyboardType.Email,
                isPassword = false,
                showPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            SoftField(
                value = contactNumber,
                onValueChange = { contactNumber = it },
                placeholder = "Enter Contact Number",
                bg = fieldBg,
                keyboardType = KeyboardType.Number,
                isPassword = false,
                showPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            SoftField(
                value = idNumber,
                onValueChange = { idNumber = it },
                placeholder = "Enter ID Number",
                bg = fieldBg,
                keyboardType = KeyboardType.Number,
                isPassword = false,
                showPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            SoftField(
                value = department,
                onValueChange = { department = it },
                placeholder = "Enter Department",
                bg = fieldBg,
                keyboardType = KeyboardType.Text,
                isPassword = false,
                showPassword = true
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Password
            SoftField(
                value = password,
                onValueChange = { password = it },
                placeholder = "Password",
                bg = fieldBg,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                showPassword = showPassword
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Confirm Password
            SoftField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Confirm Password",
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
                Text(
                    text = "Show Password",
                    fontSize = 12.sp,
                    color = SubTextLabel
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .background(fieldBg, RoundedCornerShape(14.dp))
                    .clickable { openDatePicker() }
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (dob.isBlank()) "Birthdate" else dob,
                    color = if (dob.isBlank()) TextBoxPlaceholder else TextBoxText,
                    fontSize = 13.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Open date picker",
                    tint = TextBoxText
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = {
                    isLoading = true

                    // NOTE: your current register() only accepts (email, name, password)
                    // When you update backend, include contactNumber/idNumber/department/dob.
                    authViewModel.register(email, name, password) { success, errorMessage ->
                        if (success) {
                            isLoading = false
                            navController.navigate("Login_Screen") {
                                popUpTo("Registration_Screen") { inclusive = true }
                            }
                        } else {
                            isLoading = false
                            AppUtil.showToast(context, errorMessage ?: "Something went wrong")
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = headerColor)
            ) {
                Text(
                    text = if (isLoading) "Creating Account" else "SIGN UP",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextOnDark
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Already Registered? ",
                    fontSize = 12.sp,
                    color = SubTextLabel
                )
                Text(
                    text = "Login Here",
                    fontSize = 12.sp,
                    color = TextOnWhite,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { navController.navigate("Login_Screen") }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun SoftField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    bg: Color,
    keyboardType: KeyboardType,
    isPassword: Boolean,
    showPassword: Boolean
) {
    val transformation =
        if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        placeholder = { Text(placeholder, fontSize = 13.sp, color = TextBoxPlaceholder) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = transformation,
        shape = RoundedCornerShape(14.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = bg,
            unfocusedContainerColor = bg,
            disabledContainerColor = bg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = DarkGrayBlue,
            focusedTextColor = TextBoxText,
            unfocusedTextColor = TextBoxText
        )
    )
}