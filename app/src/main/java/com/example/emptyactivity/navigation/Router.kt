package com.example.emptyactivity.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.Pages.CreatePost
import com.example.emptyactivity.Pages.Home
import com.example.emptyactivity.Pages.SignIn
import com.example.emptyactivity.Pages.ViewAccount

val LocalNavController = compositionLocalOf<NavController> { error("No nav controller found :(")  }

@Composable
fun Router(){
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Routes.SignUp.route) {
            composable(Routes.Home.route){
                Home()
            }

            composable(Routes.CreatePost.route){
                CreatePost()
            }

            composable(Routes.Account.route){
                ViewAccount()
            }

            composable(Routes.SignUp.route){
                SignIn()
            }
        }
    }
}