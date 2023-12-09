package com.example.emptyactivity.Layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * The main layout of the app
 * @param content the content to display
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    content: @Composable () -> Unit
){
    Scaffold(
        bottomBar = { BottomBar() },
        content = {
            Column(modifier = Modifier.padding(it)
                                        .background(Color(0xFFE2A62C))
            ) {
                content()
            }
        }
    )
}

