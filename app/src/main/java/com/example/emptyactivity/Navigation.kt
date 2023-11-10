package com.example.emptyactivity

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.login.LoginViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emptyactivity.login.LoginScreen
import com.example.emptyactivity.login.SignUpScreen


enum class LoginRoutes{
    SignUp,
    SignIn,
}

enum class HomeRoutes{
    Home,
    Details,

}
@Composable
fun Navigation(navController: NavHostController = rememberNavController( ),
               loginViewModel: LoginViewModel) {
    
    NavHost(
        navController = navController,
        startDestination = LoginRoutes.SignIn.name ){

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