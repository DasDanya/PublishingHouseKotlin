package com.example.publishinghousekotlin.http.requests


import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern


data class LoginRequest(

    @NotBlank(message = "Необходимо ввести электронную почту")
    @Email(message = "Неверный адрес электронной почты")
    private val email:String,

    @NotBlank(message = "Необходимо ввести пароль")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "Пароль должен состоять минимум из 8 символов. В нем должна быть, как минимум, 1 заглавная буква, 1 строчная буква и 1 цифра. Пробелы недопустимы")
    private val password: String
)