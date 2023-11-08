package com.example.emptyactivity.Pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import com.example.emptyactivity.DataModels.Post
import com.example.emptyactivity.Layout.MainLayout
import java.time.LocalDateTime
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.emptyactivity.DataModels.PostRepositoryFirestore
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.Layout.MainLayout
import kotlinx.coroutines.CoroutineScope


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Home(){

    val vm = PostViewModel(PostRepositoryFirestore())

    val postsFromFirebase = vm.allPosts.collectAsState()

    MainLayout {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(postsFromFirebase.value) { post ->
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
                            .background(Color.Blue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "❤️  ${post._likes.count()}",
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
        }
    }
}