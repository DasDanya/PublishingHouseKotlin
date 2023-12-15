package com.example.publishinghousekotlin.dtos

import java.math.BigDecimal

data class BookingSimpleAcceptDTO(
    val id: Long,
    val cost: BigDecimal
){

}