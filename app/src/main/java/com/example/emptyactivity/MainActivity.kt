package com.example.emptyactivity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emptyactivity.Auth.AuthRepository
import com.example.emptyactivity.DataModels.CommentRepositoryFirestore
import com.example.emptyactivity.DataModels.CommentViewModel
import com.example.emptyactivity.DataModels.Post
import com.example.emptyactivity.DataModels.PostRepositoryFirestore
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.DataModels.ProfileRepositoryFirestore
import com.example.emptyactivity.DataModels.User
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.Pages.SignIn
import com.example.emptyactivity.login.LoginViewModel
import com.example.emptyactivity.navigation.Router
import com.example.emptyactivity.ui.theme.EmptyActivityTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EmptyActivityTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val postModel = PostViewModel(PostRepositoryFirestore())
                    val loginViewModel = LoginViewModel(AuthRepository())
                    val commentModel = CommentViewModel(CommentRepositoryFirestore())
                    val userModel = UserViewModel(ProfileRepositoryFirestore())

                    var user = User("1","1","1", false, listOf(""), listOf(""), listOf(""), listOf(""), 100)

                    Router(postModel, commentModel, loginViewModel, userModel)
                }
            }
        }
    }
}