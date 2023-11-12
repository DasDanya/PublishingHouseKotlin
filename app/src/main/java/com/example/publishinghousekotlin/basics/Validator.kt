package com.example.publishinghousekotlin.basics

class Validator {
    fun isValidEmail(email:String): Boolean{
        val emailRegex = Regex("[a-z0-9._%+-]+@[a-z0-9.-]+\\\\.[a-z]{2,4}")

        return email.matches(emailRegex)
    }
}