package com.example.emptyactivity.DataModels

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class Post(val _id: String, val _name: String,val _postDate: LocalDateTime, val _title: String, val _content: String, val _likes: List<String>, val _controversialRating: Int, val _controversialTypes: List<String>) {

}