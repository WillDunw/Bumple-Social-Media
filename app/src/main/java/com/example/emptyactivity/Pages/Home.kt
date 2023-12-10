package com.example.emptyactivity.Pages

import android.content.Context
import android.os.Build
import android.widget.Button
import android.widget.Toast
import androidx.activity.compose.ReportDrawn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.DataModels.Comment
import com.example.emptyactivity.DataModels.CommentViewModel
import com.example.emptyactivity.DataModels.PostViewModel
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import com.example.emptyactivity.DataModels.User
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes
import java.util.Random


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(postViewModel: PostViewModel, commentViewModel: CommentViewModel, userModel: UserViewModel){

    val commentsFromFirebase = commentViewModel.allComments.collectAsState()
    var isCommenting by rememberSaveable { mutableStateOf(false) }
    var postCommenting : Post? = null

    //disgusting I agree, pls propose better solution if you can find
    val postsFiltered = postViewModel.allPosts.collectAsState().value.filter { p ->
        canDisplayHomeScreen(p, userModel.currentUser)
    }.shuffled()

    MainLayout {

        Box(modifier = Modifier.fillMaxSize()) {
            if (isCommenting) {
                CommentingBox(listComment = commentsFromFirebase.value, post = postCommenting, commentViewModel = commentViewModel, userModel)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    items(postsFiltered) { post ->
                        PostBox(
                            post = post,
                            postViewModel,
                            listComment = commentsFromFirebase.value,
                            setCommentingCallback = {isCommenting = true; postCommenting = post},
                            username = userModel.currentUser._username
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
    setCommentingCallback: () -> Unit,
    username: String
){
    var heartDisplayColor by rememberSaveable{ mutableStateOf(determineHeartDisplayColor(post, username))}

    val navController = LocalNavController.current


    val context = LocalContext.current

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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(Modifier.weight(1f))
                Text(
                    text = post._title,
                    fontWeight = FontWeight.Bold,
                )
                Box(Modifier.weight(1f)) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable(onClick = { context.Report() }),
                        text = "\uD83D\uDEA9",
                        textAlign = TextAlign.End
                    )
                }
            }
            Text(text = "By: " + post._username,
                modifier = Modifier
                    .clickable {
                        navController.navigate(Routes.ViewOtherAccount.route + "/${post._username}")
                    })
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
                .background(Color(0xFFBA872B))
                .clickable {
                    onLikeButtonClick(post, postViewModel, username)
                    heartDisplayColor = determineHeartDisplayColor(post, username)
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
                .background(Color(0xFFBA872B))
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
fun CommentingBox(listComment: List<Comment>, post: Post?, commentViewModel: CommentViewModel, userModel: UserViewModel ){
    var listDisplay = listComment.filter { c ->
        c._postId == post?._id && post != null
    }

    var comment by rememberSaveable {
        mutableStateOf("")
    }

    val navController = LocalNavController.current

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
                colors = ButtonDefaults.buttonColors(containerColor =  Color(186,135,43)),
                onClick = {
                    if(comment != ""){
                        commentViewModel.addPost(Comment(_id = "1", _userId = userModel.currentUser._username, _postId = post?._id!!, _comment = comment))

                        comment = ""
                        commentViewModel.refresh() } }) {
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
                        Text(text = it._userId,
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Routes.ViewOtherAccount.route + "/${it._userId}")
                                })
                        Text(text = it._comment)
                    }

                }
            }
        }
    }

}


fun Context.Report() {
    Toast.makeText(this, "Thanks for reporting this post\n       We \"will\" look into it", Toast.LENGTH_SHORT).show()
}



//NEED USERNAME PLUGGED HERE TOO
fun onLikeButtonClick(post: Post, postViewModel: PostViewModel, username: String){
    if(post._likes.contains(username)){
        post._likes.remove(username)
    } else {
        post._likes.add(username)
    }

    postViewModel.likePost(post)
}

//NEED USERNAME PLUGGED HERE
fun determineHeartDisplayColor(post: Post, username: String) :  String{
    if(post._likes.contains(username)){
        return "❤️"
    }

    return "\uD83E\uDD0D"
}

fun canDisplayHomeScreen(post: Post, user:User) : Boolean{
    return post._controversialRating <= user._maxControversialRating &&
            //this line explained: true if either there is no controversial type or the controversial type of the post is not in the user's sensitive types
            //values need to be replaced too for the controversial rating above to be the user's rating and the type to be the user's
            //and the username values at the bottom need to be replaced too
            (post._controversialType == Post.ControversialType.None || !user.controversialTypes.contains(post._controversialType))
            && post._username != user._username && !post._likes.contains(user._username)
}