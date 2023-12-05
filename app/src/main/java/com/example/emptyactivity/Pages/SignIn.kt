package com.example.emptyactivity.Pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.DataModels.User
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.R
import com.example.emptyactivity.login.LoginViewModel
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    userModel: UserViewModel,
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current
    val navHost = LocalNavController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFfac55c)),
            horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = "Bumble",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 70.sp),
        )

        Image(
            painter = painterResource(id = R.drawable.bumble),
            contentDescription = "Bumble logo",
            modifier = Modifier.size(200.dp)
        )

        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )

        if (isError) {
            Text(text = loginUiState?.loginError ?: "sign in error", color = Color.Red)
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.username ?: "",
            onValueChange = {
                loginViewModel?.onUsernameChange(it)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            label = {
                Text(text = "email")
            },
            isError = isError,
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.password ?: "",
            onValueChange = { loginViewModel?.onPasswordChange(it) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            label = {
                Text(text = "password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
        )

        Button(onClick = {
            loginViewModel?.loginUser(context)

            //if the user that is logging in alr has an account, almost every time it will
            //this is just for backwards compatibility again
            if (userModel.allUsers.value.any { u -> u._email == loginUiState?.username }) {
                userModel.currentUser = userModel.allUsers.value.first { u -> u._email == loginUiState?.username }
            } else {
                val currentUser = User(loginUiState?.username!! , loginUiState?.username?.substringBefore('@')!!, "password", false, mutableListOf(""), mutableListOf(""), mutableListOf(""), mutableListOf(""), mutableListOf(),100)

                userModel.saveUser(currentUser)
                userModel.currentUser = currentUser
            }
        },
//            modifier = Modifier
//                .background(Color(0xFFBA872B))
        ) {
            Text(
                text = "Login",

            )
        }
        Spacer(modifier = Modifier.size(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "don't have an account?")
            Spacer(modifier = Modifier.size(8.dp))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            TextButton(onClick = { navHost.navigate(Routes.SignUp.route) }) {
                Text(text = "Sign Up")
            }
        }

        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                if(userModel.allUsers.value.any { u ->
                        u._email == loginViewModel?.currentUser?.email ?: loginUiState?.username!!
                    }) {
                    userModel.currentUser = userModel.allUsers.value.first { u ->
                        u._email == loginViewModel?.currentUser?.email ?: loginUiState?.username!!
                    }
                }
                //this is only for backwards compatibility
                else {
                    val currentUser = User(loginViewModel?.currentUser?.email ?: loginUiState?.username!!, (loginViewModel?.currentUser?.email ?: loginUiState?.username!!).substringBefore('@'), "password", false, mutableListOf(""), mutableListOf(""), mutableListOf(""), mutableListOf(""), mutableListOf(),100)

                    userModel.saveUser(currentUser)
                    userModel.currentUser = currentUser
                }
                onNavToHomePage.invoke()
            }
        }
    }
}
