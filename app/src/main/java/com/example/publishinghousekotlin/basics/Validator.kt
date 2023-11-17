package com.example.publishinghousekotlin.basics

class Validator {
    fun isValidEmail(email:String): String?{

        if(email.length < 5){
            return "Минимальная длина 5 символов"
        }

        if(email.length > 60) {
            return "Максимальная длина 60 символов"
        }

        val emailRegex = Regex("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")
        if(!email.matches(emailRegex)){
            return "Некорректный ввод электронной почты"
        }

        return null
    }

    fun isValidPassword(password:String): String?{
        if(password.length < 8){
            return "Минимальная длина 8 символов"
        }

        if(password.length > 30){
            return "Максимальная длина 30 символов"
        }

        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}\$")
        if(!password.matches(passwordRegex)){
            return "Пароль должен иметь строчную, пропискную букву и цифру"
        }

        return null
    }

    fun isValidConfirmPassword(password:String?, confirmPassword:String?): String?{
        if(!confirmPassword.equals(password)){
            return "Пароли не совпадают"
        }

        return null
    }

    fun isValidUsername(username:String): String?{
        if(username.length == 0){
            return "Необходимо ввести наименование"
        }

        return null
    }

    fun isValidPhone(phone:String): String?{
        val phoneRegex = Regex("\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}")

        if(!phone.matches(phoneRegex)){
            return "Формат номера телефона: +7-###-###-##-##"
        }

        return null
    }

}