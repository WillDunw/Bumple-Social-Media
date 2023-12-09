package com.example.emptyactivity.DataModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * The view model that is responsible for the comments.
 *
 * @property allComments Holds all the comments that are in the database.
 * @param commentRepository The interface to interact with the comment repository.
 * */
class CommentViewModel(private val commentRepository: CommentRepository) : ViewModel() {

    private val _allComments = MutableStateFlow(listOf<Comment>())

    val allComments: StateFlow<List<Comment>> = _allComments.asStateFlow()

    init {
        viewModelScope.launch {
            commentRepository.getAllComments().collect() {
                _allComments.value = it
            }
        }
    }

    fun addPost(comment: Comment){
        viewModelScope.launch {
            commentRepository.saveComment(comment)
        }
    }

    fun likePost(post: Post, comment: Comment){
        viewModelScope.launch {
            commentRepository.commentOnPost(post._id, comment)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            commentRepository.getAllComments().collect {
                _allComments.value = it
            }
        }
    }

}