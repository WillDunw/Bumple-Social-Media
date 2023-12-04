package com.example.emptyactivity.DataModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

/**
 * The view model that is responsible for the user.
 *
 * @property allUsers Holds all the posts that are in the database.
 * @property currentUser The currently logged in user.
 * @param userRepository The interface to interact with the comment repository.
 * */
class UserViewModel(private val userRepository: ProfileRepository) : ViewModel() {
    private val _allUsers = MutableStateFlow(listOf<User>())

    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    var currentUser = User("1","1","1", false, mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(), mutableListOf(),100)

    init {
        viewModelScope.launch {
            userRepository.getProfiles().collect { allUsersFireStore ->
                if(allUsersFireStore.isNotEmpty())
                _allUsers.value = allUsersFireStore
            }
        }
    }

    fun saveUser(user: User){
        viewModelScope.launch {
            userRepository.saveProfile(user)
        }
    }

    fun refresh() {
        viewModelScope.launch {
            userRepository.getProfiles().collect {
                if(it.isNotEmpty())
                    _allUsers.value = it
            }
        }
    }
}