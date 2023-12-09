package com.example.emptyactivity.DataModels

import kotlinx.coroutines.flow.Flow

/*
* An interface that represents all the methods the post repository is supposed to implement.
* */
interface PostRepository {
    suspend fun savePost(post: Post) : Boolean
    suspend fun getAllPosts() : Flow<List<Post>>
    suspend fun likePost(post: Post)
    suspend fun delete(id: String)
}