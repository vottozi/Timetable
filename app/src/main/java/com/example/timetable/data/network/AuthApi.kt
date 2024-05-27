package com.example.timetable.data.network

import com.example.timetable.data.responses.LoginResponse
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApi {

//    @POST("login")
//    suspend fun login(
//        @Field("email") email: String,
//        @Field("passwd") passwd: String
//    ) : LoginResponse

    @POST("login")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse

}