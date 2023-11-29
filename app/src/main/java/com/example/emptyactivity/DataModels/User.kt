package com.example.emptyactivity.DataModels

class User( val _email: String,val _username: String,val _password: String?,val _isBanned: Boolean?,val _followers: MutableList<String>,val _following: MutableList<String>,val _posts: MutableList<String>,val _likedPosts: MutableList<String>, val controversialTypes: MutableList<Post.ControversialType>,val _maxControversialRating: Int) {

}