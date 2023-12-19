package com.example.publishinghousekotlin.dtos

import com.example.publishinghousekotlin.models.PrintingHouse
import com.example.publishinghousekotlin.models.StatusOfBooking
import java.math.BigDecimal
import java.time.LocalDate

data class BookingSendDTO(
   val id: Long,
   var status: String,
   var startExecution: LocalDate,
   var endExecution: LocalDate?,
   var cost: BigDecimal,
   var printingHouse: PrintingHouse?,
   var productsWithMargin: List<ProductWithEditionDTO>?,
   var idsOfEmployees: List<Long>?
) {
   constructor(): this(0, StatusOfBooking.WAITING.name, LocalDate.now(), null, BigDecimal(0), null, null, null)
}