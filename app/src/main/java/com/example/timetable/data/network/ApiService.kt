package com.example.timetable.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("/get/groups")
    suspend fun getGroups(): List<String>

    @GET("/get/subjects")
    suspend fun getSubjects(): List<String>

    @GET("/get/rooms")
    suspend fun getRooms(): List<String>

    @GET("/get/teachers")
    suspend fun getTeachers(): List<String>
}

object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("192.168.00.00:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}