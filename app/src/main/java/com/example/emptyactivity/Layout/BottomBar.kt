package com.example.emptyactivity.Layout

import androidx.compose.foundation.background
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes

@Composable
fun BottomBar(){
    val navController = LocalNavController.current

    BottomAppBar(containerColor = Color(213,228,174)) {
        if(navController.currentBackStackEntry?.destination?.route == Routes.SignUp.route){
            Footer()
        } else {
            NavigationBottomBar()
        }

    }
}


