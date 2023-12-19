package com.example.publishinghousekotlin.repositories

import com.example.publishinghousekotlin.MyApplication
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.adapters.LocalDateAdapter
import com.example.publishinghousekotlin.dtos.BookingSendDTO
import com.example.publishinghousekotlin.http.JwtInterceptor
import com.example.publishinghousekotlin.http.responses.MessageResponse
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate

class BookingRepository {

    private val client = OkHttpClient.Builder().addInterceptor(JwtInterceptor()).build()
    private val gson = GsonBuilder().registerTypeAdapter(LocalDate::class.java, LocalDateAdapter()).create()
    private val apiUrl = MyApplication.instance.applicationContext.resources.getString(R.string.server) + "/api/bookings"


    suspend fun add(booking: BookingSendDTO): MessageResponse?{
        val bookingAsJson = gson.toJson(booking)
        val mediaType = "application/json; chapset=utf-8".toMediaType()
        val body = bookingAsJson.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$apiUrl/add")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        if(response.code == 200 || response.code == 409){
            return MessageResponse(response.code, response.body!!.string())
        }

        return null
    }

}