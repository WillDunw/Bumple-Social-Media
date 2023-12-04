package com.example.emptyactivity.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.DataModels.CommentViewModel
import com.example.emptyactivity.DataModels.Post
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.Layout.MainLayout
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ViewOtherPersonAccount(
    userViewModel: UserViewModel,
    userName: String,
    postViewModel: PostViewModel,
    commentViewModel: CommentViewModel
) {

    val user = userViewModel.allUsers.value.first { u -> u._username == userName }
    user._followers.remove("")
    user._following.remove("")

    var userPosts = postViewModel.allPosts.collectAsState().value.filter { p ->
        p._username == userName
    }.sortedBy { p -> p._postDate }

    val commentsFromFirebase = commentViewModel.allComments.collectAsState().value
    var isCommenting by rememberSaveable { mutableStateOf(false) }
    var postCommenting: Post? = null

    var isFollowing by rememberSaveable {
        mutableStateOf(user._followers.contains(userViewModel.currentUser._username))
    }

    //gross, so many states, but have to do this for reasons I'm not writing here
    var followerCount by rememberSaveable {
        mutableStateOf(user._followers.size)
    }

    MainLayout {
        if (isCommenting) {
            CommentingBox(
                listComment = commentsFromFirebase,
                post = postCommenting,
                commentViewModel = commentViewModel,
                userViewModel
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = userName,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    if(isFollowing){
                        user._followers.remove(userViewModel.currentUser._username)
                        userViewModel.currentUser._following.remove(user._username)
                        followerCount--
                    } else {
                        user._followers.add(userViewModel.currentUser._username)
                        userViewModel.currentUser._following.add(user._username)
                        followerCount++
                    }

                    isFollowing = !isFollowing
                    userViewModel.saveUser(user)
                    userViewModel.saveUser(userViewModel.currentUser)
                    userViewModel.refresh()
                }) {
                    Text(text = if(isFollowing) "Unfollow" else "Follow")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // chatgpt -> how can i have a grid like box section
                    //weird code but trust it was weird behaviour
                    SmallBox(text = "Posts", userPosts.size.toString())
                    SmallBoxFollower(
                        text = "Followers",
                        followerCount.toString(),
                        userName
                    )
                    SmallBoxFollowing(
                        text = "Following",
                        user._following.size.toString(),
                        userName
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))


                LazyColumn() {
                    items(userPosts) {
                        PostBox(
                            post = it,
                            postViewModel = postViewModel,
                            commentsFromFirebase,
                            setCommentingCallback = {
                                isCommenting = true; postCommenting = it
                            },
                            userViewModel.currentUser._username
                        )
                    }
                }

            }
        }
    }
}