package com.example.bantaycampus01.screens.User

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.navigation.NavController

@Composable
fun MainMenu(modifier: Modifier = Modifier, navController: NavController){
    Column(
        modifier = Modifier,
    ) {
        Text("This is the main menu")
    }
}