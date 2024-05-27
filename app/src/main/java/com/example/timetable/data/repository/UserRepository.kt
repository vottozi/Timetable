package com.example.timetable.data.repository

import com.example.timetable.data.network.UserApi

class UserRepository(
    private val api: UserApi
) : BaseRepository() {

   suspend fun getUser() = safeApiCall {
       api.getUser()
   }
}