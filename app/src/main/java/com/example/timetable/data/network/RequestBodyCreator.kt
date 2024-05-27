package com.example.timetable.data.network

import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object RequestBodyCreator {

    fun createLoginRequestBody(email: String, passwd: String): RequestBody {
        val jsonObject = JsonObject().apply {
            addProperty("email", email)
            addProperty("passwd", passwd)
        }
        return jsonObject.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
    }

}
