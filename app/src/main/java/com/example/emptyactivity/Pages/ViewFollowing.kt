package com.example.emptyactivity.Pages

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.navigation.NavBarIcon.Companion.items

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ViewFollowing(userModel: UserViewModel, username : String) {
    val users = userModel.allUsers.value.first { u -> u._username == username }
    // display all the usernames of the users that the current user is following
    Box {
        Text(text = "Following: ")

        LazyColumn(){
            items(users._following) { following ->
                Text(text = following)
            }
        }

    }
}