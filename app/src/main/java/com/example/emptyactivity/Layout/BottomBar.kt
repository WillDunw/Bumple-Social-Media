package com.example.emptyactivity.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(){
    BottomAppBar(
        modifier = Modifier
            .background(color = Color.Black)
    ) {
        DisplayFooter()
    }
}

/**
 * Display the Footer of the app
 * Simply Copyright stuff
 */
@Composable
fun DisplayFooter() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
    ){
        Text(text = "Bumble Copyright 2023", color = Color.Black)
    }
}


