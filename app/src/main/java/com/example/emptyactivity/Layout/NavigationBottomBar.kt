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

/**
 * compose function is responsible for displaying the navigation bar.
 * it will display the nav bar icons and will navigate to the appropriate route when clicked.
 */
@Composable
fun NavigationBottomBar(){
    //  accesses the navController from the local composition

    val navController = LocalNavController.current

    // getting the current destination and setting it to a state
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // setting a custom colour
    BottomNavigation(backgroundColor = Color(213,228,174),
        contentColor = Color(0,0,0),
        elevation = 0.dp) {
        NavBarIcon.items.forEachIndexed{ index, item ->// goes through the defined NavBarIcon items and create NavigationBarItem for each

            NavigationBarItem(selected =
            currentDestination?.hierarchy?.any {
                currentDestination.route?.substringBefore("/") ==
                        item.route.substringBefore("/") } == true,
                onClick = { navController.navigate(item.route) }, //  item click to navigate to the associated route

                icon = { Icon(item.icon, contentDescription = item.route) }) // setting the icon for the nav bar item

        }
    }
}