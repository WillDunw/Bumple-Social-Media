package com.example.emptyactivity.DataModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: ProfileRepository) : ViewModel() {
    private val _allUsers = MutableStateFlow(listOf<User>())

    val allUsers: StateFlow<List<User>> = _allUsers.asStateFlow()

    var currentUser = User("1","1","1", false, listOf(""), listOf(""), listOf(""), listOf(""), 100)

    init {
        viewModelScope.launch {
            userRepository.getProfiles().collect() { allUsersFireStore ->
                _allUsers.value = allUsersFireStore
            }
        }
    }

    fun saveUser(user: User){
        viewModelScope.launch {
            userRepository.saveProfile(user)
        }
    }
}