package com.example.emptyactivity.DataModels

class User( val _email: String,val _username: String,val _password: String?,val _isBanned: Boolean?,val _followers: List<String>,val _following: List<String>,val _posts: List<String>,val _likedPosts: List<String>,val _maxControversialRating: Int) {

}