package com.example.emptyactivity.DataModels

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun saveProfile(userProfile: User)

    suspend fun getProfile(username: String): Flow<User>

    suspend fun getProfiles(): Flow<List<User>>
}