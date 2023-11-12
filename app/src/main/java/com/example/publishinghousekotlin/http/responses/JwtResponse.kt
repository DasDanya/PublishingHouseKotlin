package com.example.publishinghousekotlin.http.responses

import com.example.publishinghousekotlin.models.User

data class JwtResponse(

    val token:String,

    val type:String,

    val user: User

)