package com.example.emptyactivity.Layout

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes


/**
 * composable function that represents the app nav bar
 *  content varies on the routes nav .
 */

@Composable
fun BottomBar(){
    // accessing the NavController from the local composition
    val navController = LocalNavController.current

    // setting a custom colour
    BottomAppBar(containerColor = Color(213,228,174)) {
        // setting the nav route
        if(navController.currentBackStackEntry?.destination?.route == Routes.SignUp.route){
            Footer()
        } else {
            // show the regular nav bar for all other pages
            NavigationBottomBar()
        }

    }
}


