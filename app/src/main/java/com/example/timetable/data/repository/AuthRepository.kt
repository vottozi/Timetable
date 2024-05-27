package com.example.timetable.data.repository

import android.util.Log
import com.example.timetable.data.UserPreferences
import com.example.timetable.data.network.AuthApi
import com.example.timetable.data.network.RequestBodyCreator
import com.example.timetable.data.responses.LoginResponse

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(email: String, passwd: String): LoginResponse {
        val requestBody = RequestBodyCreator.createLoginRequestBody(email, passwd)
        val response = api.login(requestBody)
        saveAuthToken(response.token)
        return response
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }

    suspend fun saveUserRole(role: String) {
        preferences.saveRole(role)
    }

}