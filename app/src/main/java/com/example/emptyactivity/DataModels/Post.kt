package com.example.emptyactivity.DataModels

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

/**
 * A container to represent the data a post holds.
 *
 * @property _id The individual post id.
 * @property _username The username of the user that posted the comment.
 * @property _postDate The date the post was made
 * @property _title The post title.
 * @property _content The post content.
 * @property _likes A list of all the usernames that have liked the post.
 * @property _controversialRating A rating that dictate how controversial the post is. A number to be paired with the controversy type.
 * @property _controversialType A controversy type a post may have since people may have different sensibilities to different things.
 * */
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