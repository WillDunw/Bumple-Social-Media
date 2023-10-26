package com.example.emptyactivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.bumble),
            contentDescription = "Welcome to the app",
            modifier = Modifier
                .size(100.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Sign In",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Email text field
        val emailState = remember { mutableStateOf("") }
        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password text field
        val passwordState = remember { mutableStateOf("") }
        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(onClick = {

        }) {
            Text("Submit")
        }
    }
}


