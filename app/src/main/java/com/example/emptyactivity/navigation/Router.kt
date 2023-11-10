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
import com.example.emptyactivity.HomeRoutes
import com.example.emptyactivity.LoginRoutes
import com.example.emptyactivity.Pages.CreatePost
import com.example.emptyactivity.Pages.Home
import com.example.emptyactivity.Pages.SignIn
import com.example.emptyactivity.Pages.ViewAccount
import com.example.emptyactivity.login.LoginScreen
import com.example.emptyactivity.login.SignUpScreen

val LocalNavController = compositionLocalOf<NavController> { error("No nav controller found :(")  }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Router(postViewModel : PostViewModel){
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = LoginRoutes.SignIn.name) {
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

            composable(route = LoginRoutes.SignIn.name){
                LoginScreen(onNavToHomePage = {
                    navController.navigate(HomeRoutes.Home.name){
                        launchSingleTop = true
                        popUpTo(route = LoginRoutes.SignIn.name){
                            inclusive = true
                        }
                    }
                }) {
                    navController.navigate(LoginRoutes.SignUp.name){
                        launchSingleTop = true
                        popUpTo(route = LoginRoutes.SignIn.name){
                            inclusive = true
                        }
                    }
                }
            }

            composable(route = LoginRoutes.SignUp.name){
                SignUpScreen(onNavToHomePage ={
                    navController.navigate(HomeRoutes.Home.name){
                        popUpTo(route = LoginRoutes.SignUp.name){
                            inclusive = true
                        }
                    }
                }){
                    navController.navigate(LoginRoutes.SignIn.name)
                }
            }

            composable(route = HomeRoutes.Home.name){
                // TODO: 11/10/2023
            }
        }
    }
}