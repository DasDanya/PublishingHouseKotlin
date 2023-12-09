package com.example.publishinghousekotlin.basics

import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Listener {

    private var validator = Validator()
    fun emailListener(emailText: TextInputEditText, emailContainer: TextInputLayout){
        emailText.addTextChangedListener {
            emailContainer.helperText = validator.isValidEmail(emailText.text.toString())
        }
    }

    fun passwordListener(passwordText: TextInputEditText,passwordContainer: TextInputLayout){
        passwordText.addTextChangedListener {
            passwordContainer.helperText = validator.isValidPassword(passwordText.text.toString())
        }
    }

    fun confirmPasswordListener(passwordText:TextInputEditText, confirmPasswordText: TextInputEditText, confirmPasswordContainer: TextInputLayout){
        confirmPasswordText.addTextChangedListener {
            confirmPasswordContainer.helperText = validator.isValidConfirmPassword(passwordText.text.toString(), confirmPasswordText.text.toString())
        }
    }

    fun passwordAndConfirmPasswordListener(passwordText: TextInputEditText, confirmPasswordText: TextInputEditText, passwordContainer: TextInputLayout, confirmPasswordContainer: TextInputLayout){
        passwordText.addTextChangedListener{
            passwordContainer.helperText = validator.isValidPassword(passwordText.text.toString())
            confirmPasswordContainer.helperText = validator.isValidConfirmPassword(passwordText.text.toString(), confirmPasswordText.text.toString())
        }
    }

    fun nameListener(nameText: TextInputEditText, nameContainer: TextInputLayout){
        nameText.addTextChangedListener {
            nameContainer.helperText = validator.isValidName(nameText.text.toString())
        }
    }

    fun phoneListener(phoneText: TextInputEditText, phoneContainer: TextInputLayout){
        phoneText.addTextChangedListener(object : TextWatcher{
            var beforeLength: Int = 0

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                beforeLength = phoneText.text.toString().length
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val digits = phoneText.text.toString().length

                if(beforeLength < digits) {
                    if (digits == 6 || digits == 10 || digits == 13) {
                        phoneText.setText(phoneText.text.toString() + "-")
                    }
                }
                else {
                    if (digits < 3) {
                        phoneText.setText("+7-")
                    }
                }

                phoneText.setSelection(phoneText.length())
                phoneContainer.helperText = validator.isValidPhone(phoneText.text.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    fun russianWordsListener(minLengthOfWord: Int, maxLengthOfWord: Int, russianWordsText: TextInputEditText, russianWordsContainer: TextInputLayout){
        russianWordsText.addTextChangedListener {
            russianWordsContainer.helperText = validator.isValidRussianWord(russianWordsText.text.toString(), minLengthOfWord, maxLengthOfWord)
        }
    }

    fun russianWordsListener(maxLengthOfWord: Int, russianWordsText: TextInputEditText, russianWordsContainer: TextInputLayout){
        russianWordsText.addTextChangedListener {
            if(russianWordsText.text!!.isNotEmpty()) {
                russianWordsContainer.helperText = validator.isValidRussianWord(russianWordsText.text.toString(),0, maxLengthOfWord)
            }else{
                russianWordsContainer.helperText = null
            }
        }
    }

    fun doubleNumberListener(minValue: Double, maxValue: Double, doubleAsText: TextInputEditText, doubleContainer: TextInputLayout){
        doubleAsText.addTextChangedListener {
            val doubleValue = doubleAsText.text.toString()

            if(doubleValue != "") {
                if(doubleValue.toCharArray()[0] == '.'){
                    doubleAsText.setText("")
                }else {
                    doubleContainer.helperText = validator.isValidDoubleValue(
                        doubleAsText.text.toString().toDouble(),
                        minValue,
                        maxValue
                    )
                }
            }else{
                doubleContainer.helperText = "Необходимо ввести"
            }
        }
    }

    fun colorListener(colorText:TextInputEditText, colorContainer:TextInputLayout){
        colorText.addTextChangedListener {
            colorContainer.helperText = validator.isValidColor(colorText.text.toString())
        }
    }

    fun sizeListener(sizeText: TextInputEditText, sizeContainer: TextInputLayout){
        sizeText.addTextChangedListener {
            sizeContainer.helperText = validator.isValidSizeOfMaterial(sizeText.text.toString())
        }
    }

    fun cityListener(cityText:TextInputEditText, cityContainer: TextInputLayout){
        cityText.addTextChangedListener{
            cityContainer.helperText = validator.isValidCity(cityText.text.toString())
        }
    }

    fun streetListener(streetText: TextInputEditText, streetContainer: TextInputLayout){
        streetText.addTextChangedListener{
            streetContainer.helperText = validator.isValidStreet(streetText.text.toString())
        }
    }

    fun houseNumberListener(houseNumberText: TextInputEditText, houseNumberContainer: TextInputLayout){
        houseNumberText.addTextChangedListener {
            houseNumberContainer.helperText = validator.isValidHouseNumber(houseNumberText.text.toString())
        }
    }
}