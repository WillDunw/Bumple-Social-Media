package com.example.emptyactivity.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.Pages.CreatePost
import com.example.emptyactivity.Pages.Home
import com.example.emptyactivity.Pages.SignIn
import com.example.emptyactivity.Pages.ViewAccount
import com.example.emptyactivity.login.LoginScreen
import com.example.emptyactivity.login.LoginViewModel
import com.example.emptyactivity.login.SignUpScreen

val LocalNavController = compositionLocalOf<NavController> { error("No nav controller found :(")  }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Router(postViewModel : PostViewModel, loginViewModel: LoginViewModel){
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Routes.Login.route) {
            composable(Routes.Home.route){
                Home(postViewModel)
            }

            composable(Routes.CreatePost.route){
                CreatePost(postViewModel)
            }

            composable(Routes.Account.route){
                ViewAccount(postViewModel)
            }

//            composable(Routes.SignUp.route){
//                SignIn()
//            }

            composable(route = Routes.Login.route){
                LoginScreen(onNavToHomePage = {
                    navController.navigate(Routes.Home.route){
                        launchSingleTop = true
                        popUpTo(route = Routes.Login.route){
                            inclusive = true
                        }
                    }
                }, onNavToSignUpPage = {
                    navController.navigate(Routes.Login.route){
                        launchSingleTop = true
                        popUpTo(route = Routes.Login.route){
                            inclusive = true
                        }
                    }
                }, loginViewModel = loginViewModel)
            }



            composable(route = Routes.SignUp.route){
                SignUpScreen(onNavToHomePage ={
                    navController.navigate(Routes.Home.route){
                        popUpTo(route = Routes.SignUp.route){
                            inclusive = true
                        }
                    }
                }, loginViewModel = loginViewModel
                    ){
                    navController.navigate(Routes.Login.route)
                }
            }
        }
    }
}