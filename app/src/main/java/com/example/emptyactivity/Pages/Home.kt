package com.example.emptyactivity.Pages

import android.os.Build
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import com.example.emptyactivity.DataModels.Post
import com.example.emptyactivity.Layout.MainLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.DataModels.Comment
import com.example.emptyactivity.DataModels.CommentViewModel
import com.example.emptyactivity.DataModels.PostViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(postViewModel: PostViewModel, commentViewModel: CommentViewModel){

    val postsFromFirebase = postViewModel.allPosts.collectAsState()
    val commentsFromFirebase = commentViewModel.allComments.collectAsState()
    var isCommenting by rememberSaveable { mutableStateOf(false) }
    var postCommenting : Post? = null



    MainLayout {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isCommenting) {
                CommentingBox(listComment = commentsFromFirebase.value, post = postCommenting, commentViewModel = commentViewModel)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(postsFromFirebase.value) { post ->
                        PostBox(
                            post = post,
                            postViewModel,
                            listComment = commentsFromFirebase.value,
                            setCommentingCallback = {isCommenting = true; postCommenting = post}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PostBox(
    post: Post,
    postViewModel: PostViewModel,
    listComment: List<Comment>,
    setCommentingCallback: () -> Unit
){
    var heartDisplayColor by rememberSaveable{ mutableStateOf(determineHeartDisplayColor(post))}

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
                text = post._title,
                fontWeight = FontWeight.Bold
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
                    onLikeButtonClick(post, postViewModel)
                    heartDisplayColor = determineHeartDisplayColor(post)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$heartDisplayColor  ${post._likes.count()}",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .clip(CircleShape)
                .background(Color.Blue)
                .clickable {
                    setCommentingCallback()
                },
            contentAlignment = Alignment.Center

        ){
            Text(
                text = "\uD83D\uDCAC  ${listComment.filter { c -> 
                    c._postId == post._id
                }.count() }", // NEED TO ADD COMMENTS
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentingBox(listComment: List<Comment>, post: Post?, commentViewModel: CommentViewModel ){
    var listDisplay = listComment.filter { c ->
        c._postId == post?._id && post != null
    }

    var comment by rememberSaveable {
        mutableStateOf("")
    }

    Column() {
        Row() {
            TextField(
                modifier = Modifier.width(330.dp),
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comment") },
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    ,
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                onClick = { if(comment != ""){commentViewModel.addPost(Comment(_id = "1", _userId = "username", _postId = post?._id!!, _comment = comment))
                    comment = ""} }) {
                Text("➣")
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(listDisplay) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
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
                        Text(text = it._userId)
                        Text(text = it._comment)
                    }

                }
            }
        }
    }

}


//NEED USERNAME PLUGGED HERE TOO
fun onLikeButtonClick(post: Post, postViewModel: PostViewModel){
    if(post._likes.contains("username")){
        post._likes.remove("username")
    } else {
        post._likes.add("username")
    }

    postViewModel.likePost(post)
}

//NEED USERNAME PLUGGED HERE
fun determineHeartDisplayColor(post: Post) :  String{
    if(post._likes.contains("username")){
        return "❤️"
    }

    return "\uD83E\uDD0D"
}