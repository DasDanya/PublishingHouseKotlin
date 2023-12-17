package com.example.publishinghousekotlin.dtos

/**
 * Data-класс,хранящий в себе данные о количестве продукции в заказе.
 * @author Климачков Даниил
 * @since 1.0.0
 * @property booking Заказ.
 * @property margin Количество продукции.
 */
data class CountProductsDTO(
    val booking: BookingSimpleAcceptDTO,
    val margin: Int
){

}