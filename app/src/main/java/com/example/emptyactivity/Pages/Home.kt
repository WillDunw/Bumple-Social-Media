package com.example.emptyactivity.Pages

import android.os.Build
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.DataModels.PostViewModel
import java.util.Random


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(postViewModel: PostViewModel){

    //disgusting I agree, pls propose better solution if you can find
    val postsFiltered = postViewModel.allPosts.collectAsState().value.filter { p ->
        p._controversialRating < 0 /*replace with current uer's controversial rating*/ &&
                //this line explained: true if either there is no controversial type or the controversial type of the post is not in the user's sensitive types
                //values need to be replaced too for the controversial rating above to be the user's rating and the type to be the user's
                //and the username values at the bottom need to be replaced too
                p._controversialType == Post.ControversialType.None || Post.ControversialType.values().contains(p._controversialType)
                && p._username != "username" && !p._likes.contains("username")
    }.shuffled()

    var test = postViewModel.allPosts.collectAsState().value

    MainLayout {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(postsFiltered) { post ->
                PostBox(post = post, postViewModel)
            }
        }
    }
}

@Composable
fun PostBox(post: Post, postViewModel: PostViewModel){
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
                .background(Color.Blue),
            contentAlignment = Alignment.Center

        ){
            Text(
                text = "\uD83D\uDCAC  ${post._likes.count()}", // NEED TO ADD COMMENTS
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
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