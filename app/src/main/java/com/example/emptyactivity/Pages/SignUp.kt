package com.example.emptyactivity.Pages

import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.DataModels.User
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.login.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    userModel: UserViewModel,
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    var username by rememberSaveable {
        mutableStateOf("")
    }

    var usernameError by rememberSaveable {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text= "Sign Up",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )

        if (isError) {
            Text(text = loginUiState?.signUpError?: "sign in error",color = Color.Red)
        } else if(usernameError){
            Text(text = "Username already in use.", color = Color.Red)
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.usernameSignUp?:"",
            onValueChange = {loginViewModel?.onUsernameSignUpChange(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription=  null )
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
            value = username,
            onValueChange = {
                username = it
                usernameError = userModel.allUsers.value.any { u -> u._username == username } },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription=  null )
            },
            label = {
                Text(text = "username")
            },
            isError = usernameError,
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.passwordSignUp?:"",
            onValueChange = {loginViewModel?.onPasswordSignUpChange(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription=  null )
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.confirmPasswordSignUp?:"",
            onValueChange = {loginViewModel?.onConfirmPasswordChange(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription=  null )
            },
            label = {
                Text(text = "Confirm password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
        )

        Button(onClick = {
            val testValue = "test";
            val usernameSignUp = loginUiState?.usernameSignUp
            val passwordSignUp = loginUiState?.passwordSignUp
            val confirmPasswordSignUp = loginUiState?.confirmPasswordSignUp

            if (!usernameSignUp.isNullOrBlank() && !passwordSignUp.isNullOrBlank() && !confirmPasswordSignUp.isNullOrBlank()) {
                loginViewModel?.createUser(context)
                if (passwordSignUp == confirmPasswordSignUp) {
                    // chatgpt prompt: can you make an email regex for me
                    val emailPattern = Regex("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,}\$")
                    if (emailPattern.matches(usernameSignUp ?: "")) {

                        var user = User(loginUiState?.usernameSignUp!!, username,"1", false, mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), 100)
                        userModel.saveUser(user)
                        userModel.currentUser = user
                        onNavToHomePage()
                    }
                }
            }
            else {
                Toast.makeText(context, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
            }
        }){
            Text(text = "Sign up")
        }
        Spacer(modifier = Modifier.size(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "Already have an account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = { onNavToLoginPage.invoke() }) {
                Text(text = "Sign in")
            }

        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }
    }
}