package com.example.publishinghousekotlin.models

import lombok.AllArgsConstructor
import javax.validation.constraints.*;

@AllArgsConstructor
data class User(

    private val id: Long,

    @NotBlank(message = "Необходимо ввести имя")
    @Size(max = 50, message = "Длина наименования пользователя не входит в диапазон от 1 до 50 символов")
    val name:String,

    @NotBlank(message = "Необходимо ввести номер телефона")
    @Pattern(regexp = "\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}", message = "Неверный номер телефона.Паттерн: +7-###-###-##-##")
    private val phone: String,

    @NotBlank(message = "Необходимо ввести электронную почту")
    @Email(message = "Неверный адрес электронной почты")
     val email: String
)