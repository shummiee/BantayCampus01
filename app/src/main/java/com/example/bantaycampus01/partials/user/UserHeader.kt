package com.example.bantaycampus01.partials.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bantaycampus01.R
import com.example.bantaycampus01.ui.theme.DarkGrayBlue
import com.example.bantaycampus01.ui.theme.SubTextLabel
import com.example.bantaycampus01.ui.theme.TextOnWhite

@Composable
fun UserHeader(
    userName: String,
    onProfileClick: () -> Unit = {}
) {
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
                modifier = Modifier.clickable { onProfileClick() }
            ) {
                Text(
                    text = "Hi, $userName!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextOnWhite
                )

                Text(
                    text = "View Profile",
                    fontSize = 13.sp,
                    fontStyle = FontStyle.Italic,
                    color = SubTextLabel
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

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = SubTextLabel
        )
    }
}
