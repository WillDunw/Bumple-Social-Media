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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@Composable
fun ViewAccount(postViewModel: PostViewModel) {
    val navController = LocalNavController.current

    MainLayout {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(onClick = {
                FirebaseAuth.getInstance().signOut()
                navController.navigate(Routes.Login.route)

            }) {
                Text(text = "sign Out")
            }


            Image(
                painter = painterResource(id = R.drawable.kobe),
                contentDescription = "Kobe Bryant's profile picture ",
                modifier = Modifier
                    .size(210.dp)
                    .clip(CircleShape)
                    .scale(1.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "BlackMamba24",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "#DarkHumour. I love funny jokes. #BLM <3 ",
                style = TextStyle(fontSize = 18.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SmallBox(text = "Posts", "2")
                SmallBox(text = "Followers", "81k")
                SmallBox(text = "Following", "224")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn() {
                items(2) {
                    Jokes(
                        "Funny. Non-offensive",
                        "Why did the chicken cross the road? To get to the other side."
                    )
                    Jokes(
                        "Dad joke ",
                        "Why don't skeletons fight each other? They don't have the guts!"
                    )
                }
            }

//            // Button at the top right
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp),
//                contentAlignment = Alignment.TopEnd
//            ) {
//                Button(
//                    onClick = { FirebaseAuth.getInstance().signOut() },
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .background(MaterialTheme.colorScheme.primary)
//                        .padding(12.dp),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Green,
//                        contentColor = Color.White
//                    ),
//                ) {
//                    Text("Hello")
//                }
//            }
        }
    }
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
                text = header,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text)
        }
    }
}

