package com.example.emptyactivity.Layout

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// app footer composable with our copyright stamp
@Composable
fun Footer(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ){
        Text(text = "Bumble Copyright 2023", color = Color.Black)
    }
}