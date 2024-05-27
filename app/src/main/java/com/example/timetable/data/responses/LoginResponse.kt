package com.example.timetable.data.responses


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("group")
    val group: String
)