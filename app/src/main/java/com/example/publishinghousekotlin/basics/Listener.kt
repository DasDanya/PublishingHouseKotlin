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

    fun usernameListener(usernameText: TextInputEditText, usernameContainer: TextInputLayout){
        usernameText.addTextChangedListener {
            usernameContainer.helperText = validator.isValidUsername(usernameText.text.toString())
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
}