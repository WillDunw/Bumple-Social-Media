package com.example.emptyactivity.DataModels

import kotlinx.coroutines.flow.Flow

/*
* An interface that represents all the methods the comment repository is supposed to implement.
* */
interface CommentRepository {
    suspend fun saveComment(comment: Comment) : Boolean
    suspend fun getAllComments() : Flow<List<Comment>>
    suspend fun delete(id: String)
    suspend fun commentOnPost(postId: String, comment: Comment)
}