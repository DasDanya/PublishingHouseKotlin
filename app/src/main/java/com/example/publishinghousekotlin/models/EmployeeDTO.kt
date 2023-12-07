package com.example.publishinghousekotlin.models

import java.io.Serializable
import java.time.LocalDate

data class EmployeeDTO(
    val id: Long,
    var surname: String,
    var name: String ,
    var patronymic:String,
    var phone:String,
    var email:String,
    var post: String,
    var birthday: LocalDate,
    val photo: String
): Serializable
{
    constructor(): this(0, "", "", "", "", "", "", LocalDate.now(),"")

}