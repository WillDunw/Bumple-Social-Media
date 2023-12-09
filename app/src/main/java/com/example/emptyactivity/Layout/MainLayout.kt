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
 *  main layout of the app. everything is wrapped in this composable will be displayed for every page. in other words, every
 *  page will have the nav bar (bottom bar) and the footer.
 * @param content the content to display
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLayout(
    content: @Composable () -> Unit
){
    Scaffold(
        // bottom bar has the nav bar & footer inside of it.
        bottomBar = { BottomBar() },
        content = {
            Column(modifier = Modifier.padding(it)
                                        .background(Color(0xFFE2A62C)) // setting a custom colour
            ) {
                content()
            }
        }
    )
}

