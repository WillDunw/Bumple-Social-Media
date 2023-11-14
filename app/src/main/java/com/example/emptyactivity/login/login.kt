package com.example.emptyactivity.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.magnifier
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
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current
    val navHost = LocalNavController.current


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Text(text= "Login",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black
        )

        if (isError) {
            Text(text = loginUiState?.loginError?: "sign in error",color = Color.Red)
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.username?:"",
            onValueChange = {
                loginViewModel?.onUsernameChange(it)
                            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription=  null )
            },
            label = {
                Text(text = "Email")
            },
            isError = isError,
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.password?:"",
            onValueChange = {loginViewModel?.onPasswordChange(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription=  null )
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
        )

        Button(onClick = {loginViewModel?.loginUser(context)}){
            Text(text = "Login")
        }
        Spacer(modifier =Modifier.size(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "don't have an account?")
            Spacer(modifier =Modifier.size(8.dp))
            TextButton(onClick = { navHost.navigate(Routes.SignUp.route) }) {
                Text(text = "Sign Up")
            }

        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }
    }
}






//

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current


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
        }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            value = loginUiState?.usernameSignUp?:"",
            onValueChange = {loginViewModel?.onPasswordSignUpChange(it)},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription=  null )
            },
            label = {
                Text(text = "Email")
            },
            isError = isError,
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



        Button(onClick = {loginViewModel?.createUser(context)}){
            Text(text = "Login")
        }
        Spacer(modifier =Modifier.size(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "Already have an account?")
            Spacer(modifier =Modifier.size(8.dp))
            TextButton(onClick = { onNavToLoginPage.invoke() }) {
                Text(text = "Sign in")
            }

        }

        if (loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.hasUser){
            if (loginViewModel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun PrevLoginScreen() {
    LoginScreen(
        onNavToHomePage = {},
        onNavToSignUpPage = {}
    )
}

@Preview(showSystemUi = true)
@Composable
fun PrevSignUpScreen() {
    SignUpScreen(onNavToHomePage = {/* TODO*/}, onNavToLoginPage = {/* TODO*/})
}