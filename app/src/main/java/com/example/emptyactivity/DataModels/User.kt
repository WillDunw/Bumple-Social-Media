package com.example.emptyactivity.DataModels

/**
 * A container to represent the data a post holds.
 *
 * @property _email The user email.
 * @property _username The username of the user.
 * @property _password The users password. It is not stored locally for safety.
 * @property _isBanned A bool on whether the user is banned.
 * @property _followers A list of usernames that follow this user.
 * @property _following A list of usernames that this user is following.
 * @property _posts A list of post ids that the user has posted.
 * @property _likedPosts A list of post ids that the user has liked.
 * @property controversialTypes A list of controversial types that the user is sensitive to.
 * @property _maxControversialRating The max controversial rating that this user wants to see.
 * */
class User( val _email: String,val _username: String,val _password: String?,val _isBanned: Boolean?,val _followers: MutableList<String>,val _following: MutableList<String>,val _posts: MutableList<String>,val _likedPosts: MutableList<String>, val controversialTypes: MutableList<Post.ControversialType>,val _maxControversialRating: Int) {

}