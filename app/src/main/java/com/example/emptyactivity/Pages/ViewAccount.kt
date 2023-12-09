package com.example.emptyactivity.Pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emptyactivity.Layout.MainLayout
import com.example.emptyactivity.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.ComposeNodeLifecycleCallback
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emptyactivity.DataModels.Comment
import com.example.emptyactivity.DataModels.CommentViewModel
import com.example.emptyactivity.DataModels.Post
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@Composable
fun ViewAccount(
    postViewModel: PostViewModel, commentViewModel: CommentViewModel, userViewModel: UserViewModel
) {
    val navController = LocalNavController.current
    var myPosts = postViewModel.allPosts.collectAsState().value.filter { p ->
        p._username == userViewModel.currentUser._username
    }.sortedBy { p -> p._postDate }

    var myLikedPosts = postViewModel.allPosts.collectAsState().value.filter { p ->
        p._likes.contains(userViewModel.currentUser._username) && p._username != userViewModel.currentUser._username
    }.sortedBy { p -> p._postDate }

    //only have one to track our post view, hopefully name is clear
    var isViewingLikedPosts by rememberSaveable {
        mutableStateOf(false)
    }

    val commentsFromFirebase = commentViewModel.allComments.collectAsState().value
    var isCommenting by rememberSaveable { mutableStateOf(false) }
    var postCommenting: Post? = null

    userViewModel.currentUser._followers.remove("")
    userViewModel.currentUser._following.remove("")

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

                Button(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate(Routes.Login.route)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = (Color(0xFFD5E4AE)))
                ) {
                    Text(text = "Sign Out", color = Color.Black )


                }


                Text(
                    text = userViewModel.currentUser._username,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // chatgpt -> how can i have a grid like box section
                    //weird code but trust it was weird behaviour
                    SmallBox(text = "Posts", myPosts.size.toString())
                    SmallBoxFollower(
                        text = "Followers",
                        Math.max(userViewModel.currentUser._followers.size, 0).toString(),
                        userViewModel.currentUser._username
                    )
                    SmallBoxFollowing(
                        text = "Following",
                        Math.max(userViewModel.currentUser._following.size - 1, 0).toString(),
                        userViewModel.currentUser._username
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { isViewingLikedPosts = false },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GetColorFromViewChoice(
                                !isViewingLikedPosts
                            )
                        ),
                        elevation = ButtonDefaults.buttonElevation(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "My Posts"
                        )
                    }

                    Button(
                        onClick = { isViewingLikedPosts = true },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GetColorFromViewChoice(
                                isViewingLikedPosts
                            )
                        ),
                        elevation = ButtonDefaults.buttonElevation(2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "My Liked Posts"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                LazyColumn() {
                    if (isViewingLikedPosts) {
                        items(myLikedPosts) {
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
                    } else {
                        items(myPosts) {
                            PostBoxWithDelete(
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
}

fun GetColorFromViewChoice(isChosen: Boolean): Color {
    if (isChosen) return Color(247, 212, 95)

    return Color(247, 237, 95)
}

@Composable
fun SmallBox(text: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
        Text(text = value, fontSize = 18.sp)
    }
}

@Composable
fun SmallBoxFollowing(text: String, value: String, _username: String) {
    val navController = LocalNavController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { navController.navigate(Routes.ViewFollowing.route + "/${_username}") }
    ) {
        Text(text = text, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
        Text(text = value, fontSize = 18.sp)
    }
}

@Composable
fun SmallBoxFollower(text: String, value: String, _username: String) {
    val navController = LocalNavController.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { navController.navigate(Routes.ViewFollower.route + "/${_username}") }
    ) {
        Text(text = text, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp))
        Text(text = value, fontSize = 18.sp)
    }
}

@Composable
fun Jokes(header: String, text: String) {
    val backgroundColor = Color(0xFFfff194) // // 0xff stays replace the rest with the color code

    Card(
        modifier = Modifier
            .fillMaxWidth()
            //.background(backgroundColor)
            .padding(8.dp),


        ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = header, style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}

@Composable
fun PostBoxWithDelete(
    post: Post,
    postViewModel: PostViewModel,
    listComment: List<Comment>,
    setCommentingCallback: () -> Unit,
    username: String
) {
    var heartDisplayColor by rememberSaveable { mutableStateOf(determineHeartDisplayColor(post, username)) }

    var displayPostDeleteConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    if (displayPostDeleteConfirmation) {
        AlertDialog(onDismissRequest = { displayPostDeleteConfirmation = false },

            confirmButton = {
                TextButton(onClick = {
                    displayPostDeleteConfirmation = false
                    postViewModel.deletePost(post)
                }) {
                    Text(text = "Delete")
                }
            },

            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Are you sure you want to delete?"
                )
            },

            title = { Text(text = "Delete confirmation") },

            text = { Text(text = "Are you sure you want to delete post ${post._title}?") },

            dismissButton = {
                TextButton(onClick = { displayPostDeleteConfirmation = false }) {
                    Text(text = "Cancel")
                }
            }

        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(5.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = post._title, fontWeight = FontWeight.Bold
            )
            Text(text = "By: " + post._username)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = post._content
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .clickable {
                    onLikeButtonClick(post, postViewModel, username)
                    heartDisplayColor = determineHeartDisplayColor(post, username)
                }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$heartDisplayColor  ${post._likes.count()}",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(8.dp)
            .clip(CircleShape)
            .background(Color.Blue)
            .clickable {
                if (!displayPostDeleteConfirmation) displayPostDeleteConfirmation = true
            }) {
            Text(text = "Delete", color = Color.White, modifier = Modifier.padding(8.dp))
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .clickable { setCommentingCallback() },
            contentAlignment = Alignment.Center

        ) {
            Text(
                text = "\uD83D\uDCAC  ${
                    listComment.filter { c ->
                        c._postId == post._id
                    }.count()
                }", // NEED TO ADD COMMENTS
                color = Color.White, modifier = Modifier.padding(8.dp)
            )
        }
    }
}