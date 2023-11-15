package com.example.emptyactivity.navigation

sealed class Routes(val route: String) {
    object CreatePost: Routes("CreatePostScreen")
    object Home: Routes("HomeScreen")
    object Account: Routes("AccountScreen")
    object SignUp: Routes("SignUpScreen")

    object Login: Routes("LoginRoutes")
}