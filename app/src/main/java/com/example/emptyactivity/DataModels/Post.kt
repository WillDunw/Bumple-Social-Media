package com.example.emptyactivity.DataModels

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

data class Post(val _id: String, val _username: String, val _postDate: LocalDateTime, val _title: String, val _content: String, val _likes: MutableList<String>, val _controversialRating: Int, val _controversialType: ControversialType) {
    enum class ControversialType{
        Sexual,
        Racism,
        LGBTQ,
        Gender,
        Ableism,
        Religion,
        Other,
        None
    }
}