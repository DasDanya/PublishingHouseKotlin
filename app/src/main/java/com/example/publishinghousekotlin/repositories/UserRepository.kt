package com.example.publishinghousekotlin.repositories
import com.example.publishinghousekotlin.http.requests.LoginRequest
import com.example.publishinghousekotlin.http.responses.JwtResponse
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class UserRepository(private val serverUrl: String) {

    private val client = OkHttpClient()
    private val builder = GsonBuilder()
    private val gson = builder.create()
     suspend fun signin(loginData: LoginRequest): JwtResponse?{
        val loginDataAsJson = gson.toJson(loginData)
        val mediaType = "application/json; chapset=utf-8".toMediaType()
        val body: RequestBody = loginDataAsJson.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$serverUrl/auth/signin")
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        if(response.isSuccessful){
            return gson.fromJson(response.body?.string(), JwtResponse::class.java)
        }

        return null
     }
}