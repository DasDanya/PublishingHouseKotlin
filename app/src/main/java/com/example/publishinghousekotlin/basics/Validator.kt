package com.example.publishinghousekotlin.basics

class Validator {
    fun isValidEmail(email:String): String?{

        if(email.length < 5){
            return "Минимальная длина: 5 символов"
        }

        if(email.length > 60) {
            return "Максимальная длина: 60 символов"
        }

        val emailRegex = Regex("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}")
        if(!email.matches(emailRegex)){
            return "Некорректный ввод электронной почты"
        }

        return null
    }

    fun isValidPassword(password:String): String?{
        if(password.length < 8){
            return "Минимальная длина: 8 символов"
        }

        if(password.length > 30){
            return "Максимальная длина: 30 символов"
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

    fun isValidRussianWord(word:String, minLength: Int, maxLength:Int):String?{
        if(word.length < minLength){
            return "Минимальная длина $minLength символов"
        }

        if(word.length > maxLength){
            return "Максимальная длина $maxLength символов"
        }

        val russianWordRegex = Regex("^[А-Яа-я ]+$")
        if(!word.matches(russianWordRegex)){
            return "Допускаются русские буквы и пробел"
        }

        return null
    }

    fun isValidDoubleValue(doubleValue: Double, minValue: Double, maxValue: Double): String? {
        if(doubleValue < minValue){
            return "Минимальное значение: $minValue"
        }

        if(doubleValue > maxValue){
            return "Максимальное значение: $maxValue"
        }

        val stringValue = doubleValue.toString()
        val indexOfDecimal = stringValue.indexOf('.')

        if(indexOfDecimal > 0){
            if(stringValue.length - indexOfDecimal - 1 > 2){
                return "Допустимое количество цифр после запятой = 2"
            }
        }

        return null
    }

    fun isValidColor(color:String):String?{
        if(color.length < 5){
            return "Минимальная длина: 5 символов"
        }

        if(color.length > 11){
            return "Максимальная длина: 11 символов"
        }

        val colorRegex = Regex("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?);(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?);(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")
        if(!color.matches(colorRegex)){
            return "Требуемый формат: R;G;B"
        }

        return null
    }

    fun isValidSizeOfMaterial(size:String):String?{
        if(size.isEmpty()){
            return "Необходимо ввести"
        }

        if(size.length > 2){
            return "Максимальная длина: 2 символа"
        }

        val sizeRegex = Regex("[A-C][0-9]")
        if(!size.matches(sizeRegex)){
            return "Требуемый формат:формат бумаги + размер"
        }

        return null
    }

}