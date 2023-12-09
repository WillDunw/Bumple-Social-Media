package com.example.emptyactivity.DataModels

import kotlinx.coroutines.flow.Flow

/*
* An interface that represents all the methods the user profile repository is supposed to implement.
* */
interface ProfileRepository {
    suspend fun saveProfile(userProfile: User)

    suspend fun getProfile(username: String): Flow<User>

    suspend fun getProfiles(): Flow<List<User>>
}