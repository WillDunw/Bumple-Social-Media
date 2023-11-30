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
import com.example.emptyactivity.DataModels.CommentViewModel
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.Pages.CreatePost
import com.example.emptyactivity.Pages.Home
import com.example.emptyactivity.Pages.LoginScreen
import com.example.emptyactivity.Pages.SignUpScreen
import com.example.emptyactivity.Pages.ViewAccount
import com.example.emptyactivity.Pages.ViewFollowing
import com.example.emptyactivity.Pages.ViewOtherPersonAccount
import com.example.emptyactivity.login.LoginViewModel

val LocalNavController = compositionLocalOf<NavController> { error("No nav controller found :(")  }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Router(postViewModel : PostViewModel, commentViewModel: CommentViewModel, loginViewModel: LoginViewModel, userModel: UserViewModel){
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = Routes.Login.route) {
            composable(Routes.Home.route){
                Home(postViewModel, commentViewModel, userModel)
            }

            composable(Routes.CreatePost.route){
                CreatePost(postViewModel, userModel =  userModel)
            }

            composable(Routes.Account.route){
                ViewAccount(postViewModel, commentViewModel, userModel)
            }

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
                }, loginViewModel = loginViewModel
                , userModel = userModel)
            }



            composable(route = Routes.SignUp.route){
                SignUpScreen(onNavToHomePage ={
                    navController.navigate(Routes.Home.route){
                        popUpTo(route = Routes.SignUp.route){
                            inclusive = true
                        }
                    }
                }, loginViewModel = loginViewModel,
                    userModel = userModel
                    ){
                    navController.navigate(Routes.Login.route)
                }
            }

            composable(Routes.ViewOtherAccount.route + "/{username}"){
                ViewOtherPersonAccount(
                    userViewModel = userModel,
                    userName = it.arguments?.getString("username")!!,
                    postViewModel = postViewModel,
                    commentViewModel = commentViewModel
                )
            }

            composable(Routes.ViewFollowing.route + "/{username}"){
                ViewFollowing(
                    userModel = userModel,
                    username = it.arguments?.getString("username")!!
                )
            }
        }
    }
}