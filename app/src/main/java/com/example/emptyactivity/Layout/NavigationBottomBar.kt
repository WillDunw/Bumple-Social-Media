package com.example.emptyactivity.Layout

import androidx.compose.material.BottomNavigation
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.NavBarIcon

@Composable
fun NavigationBottomBar(){
    val navController = LocalNavController.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = Color(255,197,78),
        contentColor = Color(255,197,78),
        elevation = 0.dp) {
        NavBarIcon.items.forEachIndexed{ index, item ->
            NavigationBarItem(selected =
            currentDestination?.hierarchy?.any {
                currentDestination.route?.substringBefore("/") ==
                        item.route.substringBefore("/") } == true,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.route) })

        }
    }
}