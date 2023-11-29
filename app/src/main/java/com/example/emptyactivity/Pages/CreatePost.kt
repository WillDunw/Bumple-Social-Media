package com.example.emptyactivity.Pages

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.emptyactivity.DataModels.Post
import com.example.emptyactivity.DataModels.PostViewModel
import com.example.emptyactivity.DataModels.UserViewModel
import com.example.emptyactivity.Layout.MainLayout
import com.example.emptyactivity.navigation.LocalNavController
import com.example.emptyactivity.navigation.NavBarIcon.Companion.items
import com.example.emptyactivity.navigation.Routes
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalStdlibApi::class)
@Composable
fun CreatePost(postViewModel: PostViewModel,modifier: Modifier = Modifier, userModel: UserViewModel){

    var title by rememberSaveable { mutableStateOf("")}

    var content by rememberSaveable { mutableStateOf("")}

    var controversial by rememberSaveable {
        mutableFloatStateOf(0.5f)
    }

    var controversialType by rememberSaveable {
        mutableStateOf(Post.ControversialType.None)
    }

    var dropDownExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    var navController = LocalNavController.current

    MainLayout {
        Column(modifier =
        modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Make someone laugh!",
                fontWeight = FontWeight.Bold,
                fontSize = 7.em,
                modifier = modifier.padding(bottom = 30.dp))

            Card{
                Column(modifier = modifier
                    .background(Color.White)
                    .height(300.dp)) {
                    TextField(value = title, onValueChange = {
                        if(it.length <= 35){
                            title = it
                        }
                    },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        label = {Text("Title")},
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f))

                    TextField(value = content, onValueChange = {
                        if(it.length <= 248){
                            content = it
                        }
                    },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Gray,
                            disabledTextColor = Color.Transparent,
                            containerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        label = {Text("Joke...")},
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(2f))

                    Text(text = "Controversial Scale",
                        modifier = modifier
                            .weight(0.5f)
                            .padding(start = 10.dp),
                        fontSize = 4.em)

                    Slider(
                        value = controversial, onValueChange = {
                            controversial = it
                        },
                        enabled = true,
                        modifier = modifier
                            .weight(0.5f),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(255,197,78),
                            activeTrackColor = Color(255,197,78),
                            inactiveTrackColor = Color(250,230,189),
                        )
                    )

                    Row(modifier = modifier
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Row(modifier =
                        modifier
                            .padding(end = 100.dp, start = 30.dp, top = 10.dp, bottom = 20.dp)
                            .size(150.dp, 35.dp)){
                            
                        Text(text = "Type: ",
                            modifier = modifier
                                .padding(top = 5.dp))
                            
                        Box(modifier = modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(6.dp))
                            ) {
                            Text(
                                controversialType.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clickable(onClick = { dropDownExpanded = true })
                                    .background(
                                        Color(255, 197, 78)
                                    )
                                    .padding(top = 5.dp),
                                textAlign = TextAlign.Center
                            )
                            DropdownMenu(
                                expanded = dropDownExpanded,
                                onDismissRequest = { dropDownExpanded = false }) {
                                enumValues<Post.ControversialType>().forEach {
                                    DropdownMenuItem(text = { Text(text = it.name, color = Color(0,0,0)) }, onClick = {
                                        controversialType = it
                                        dropDownExpanded = false
                                    })
                                }
                            }
                        }
                        }

                        Button(onClick = {
                            if(title.isNullOrEmpty()){
                               Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
                            } else if(content.isNullOrEmpty()){
                                Toast.makeText(context, "Content cannot be empty", Toast.LENGTH_SHORT).show()
                            } else {
                                postViewModel.addPost(
                                    Post(
                                        "default this gets set in firebase" //gross but no need to change this
                                        ,
                                        userModel.currentUser._username, // Need to fix this, when real username has been added
                                        LocalDateTime.now(),
                                        title,
                                        content,
                                        mutableListOf(),
                                        (controversial * 100).toInt(),
                                        controversialType
                                    )
                                )

                                navController.navigate(Routes.Account.route)
                            }
                        },
                            modifier = modifier
                                .padding(end = 10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(255,197,78))) {
                            Text(text  = "➤")
                        }
                    }
                }
            }
        }
    }
}