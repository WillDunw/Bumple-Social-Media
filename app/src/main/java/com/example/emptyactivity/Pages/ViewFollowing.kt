package com.example.emptyactivity.Pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.Layout.MainLayout
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes

/**
 * composable function that represents the view following screen.
 *
 * @param userModel the view model that is responsible for the user.
 * @param username the username of the user.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ViewFollowing(userModel: UserViewModel, username: String) {
    val users = userModel.allUsers.value.first { u -> u._username == username }
    val navController = LocalNavController.current

    MainLayout {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background((Color(186,135,43)))
        ) {
            Column {
                LazyColumn {
                    items(users._following) { following ->
                        if(following == null){
                            Text(text = "No Followers")
                        } else {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate(Routes.ViewOtherAccount.route + "/${following}")
                                    },
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = following,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
