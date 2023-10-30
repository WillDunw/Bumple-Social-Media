package com.example.emptyactivity.Pages

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.emptyactivity.Layout.MainLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePost(modifier: Modifier = Modifier){

    var title by rememberSaveable { mutableStateOf("")}

    var content by rememberSaveable { mutableStateOf("")}

    MainLayout {
        Column(modifier =
            modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = title, onValueChange = {

            })

            TextField(value = content, onValueChange = {

            })
        }
    }
}

@Preview
@Composable
fun Test(){
    CreatePost()
}