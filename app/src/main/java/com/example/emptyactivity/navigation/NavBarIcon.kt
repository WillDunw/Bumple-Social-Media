package com.example.emptyactivity.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarIcon(val route: String, val icon: ImageVector) {

    companion object {
        val items = listOf(
            NavBarIcon(Routes.Home.route, Icons.Default.Home),
            NavBarIcon(Routes.CreatePost.route, Icons.Default.Add),
            NavBarIcon(Routes.Account.route, Icons.Default.AccountCircle)
        )
    }
}