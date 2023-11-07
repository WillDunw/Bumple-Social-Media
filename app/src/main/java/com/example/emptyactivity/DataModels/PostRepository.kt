package com.example.emptyactivity.DataModels

import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun savePost(post: Post)
    fun getAllPosts() : List<Post>
    fun getAllPostsLiked(userId: String) : List<Post>
    fun likePost(userId: String, postId: String)
    suspend fun delete(id: String)
}