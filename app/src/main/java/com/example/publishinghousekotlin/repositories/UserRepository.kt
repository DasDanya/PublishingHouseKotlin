package com.example.publishinghousekotlin.repositories
import com.example.publishinghousekotlin.MyApplication
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.http.requests.LoginRequest
import com.example.publishinghousekotlin.http.requests.RegisterRequest
import com.example.publishinghousekotlin.http.responses.JwtResponse
import com.example.publishinghousekotlin.http.responses.MessageResponse
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class UserRepository() {

    private val client = OkHttpClient()
    private val gson = GsonBuilder().create()
    private val apiUrl = MyApplication.instance.applicationContext.resources.getString(R.string.server) + "/api/auth"

     suspend fun login(loginData: LoginRequest): JwtResponse?{

        val loginDataAsJson = gson.toJson(loginData)
        val mediaType = "application/json; chapset=utf-8".toMediaType()
        val body = loginDataAsJson.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$apiUrl/login")
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        if(response.isSuccessful){
            return gson.fromJson(response.body?.string(), JwtResponse::class.java)
        }

        return null
     }

    suspend fun register(registerData: RegisterRequest):MessageResponse?{

        val registerDataAsJson = gson.toJson(registerData)
        val mediaType = "application/json; chapset=utf-8".toMediaType()
        val body = registerDataAsJson.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$apiUrl/register")
            .post(body)
            .build()

        val response = client.newCall(request).execute()
        if(response.code == 200 || response.code == 400){
            return MessageResponse(response.code, response.body!!.string())
        }

        return null
    }
}