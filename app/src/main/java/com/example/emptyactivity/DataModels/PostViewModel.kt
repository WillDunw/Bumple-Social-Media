package com.example.emptyactivity.DataModels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * The view model that is responsible for the posts.
 *
 * @property allPosts Holds all the posts that are in the database.
 * @param postRepository The interface to interact with the comment repository.
 * */
class PostViewModel(private val postRepository: PostRepository) : ViewModel() {

    private val _allPosts = MutableStateFlow(listOf<Post>())

    val allPosts: StateFlow<List<Post>> = _allPosts.asStateFlow()

    init {
        viewModelScope.launch {
            postRepository.getAllPosts().collect() {
                _allPosts.value = it
            }
        }
    }

    fun addPost(post: Post){
        viewModelScope.launch {
            postRepository.savePost(post)
        }
    }

    fun likePost(post: Post){
        viewModelScope.launch {
            postRepository.likePost(post)
        }
    }

    fun deletePost(post: Post){
        viewModelScope.launch {
            postRepository.delete(post._id)
        }
    }

}