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
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.SubTextLabel
import com.example.bantaycampus01.ui.theme.TextBoxBg
import com.example.bantaycampus01.ui.theme.TextBoxPlaceholder
import com.example.bantaycampus01.ui.theme.TextBoxText
import com.example.bantaycampus01.ui.theme.TextOnDark
import com.example.bantaycampus01.ui.theme.TextOnWhite
import com.example.bantaycampus01.ui.theme.White
import java.util.Calendar

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier){
    val headerColor = DarkGrayBlue
    val fieldBg = TextBoxBg

    val scrollState = rememberScrollState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var dob by remember { mutableStateOf("") }

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
        }

        // ✅ Scrollable content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp)
                .background(White)
                .verticalScroll(scrollState) // ✅ scroll enabled
                .padding(horizontal = 30.dp)
                .padding(top = 28.dp)
                .padding(bottom = 30.dp),     // ✅ extra bottom space
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

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },

                placeholder = {
                    Text("Name")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = {email = it},

                placeholder = {
                    Text("Email")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )

            Spacer(modifier = Modifier.height(14.dp))

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {password = it},

                placeholder = {
                    Text("Password")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),

                visualTransformation =
                    if (showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(14.dp))

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it},

                placeholder = {
                    Text("Confirm Password")
                },

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),

                visualTransformation =
                    if (showPassword)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation()
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
                    text = if (dob.isBlank()) "Select" else dob,
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
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = headerColor)
            ) {
                Text(
                    "SIGN UP",
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
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}