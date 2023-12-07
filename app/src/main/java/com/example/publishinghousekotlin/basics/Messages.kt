package com.example.publishinghousekotlin.basics

import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class Messages {

    fun showError(message:String, root: ViewGroup){
        val snackbar = Snackbar.make(root,message, Snackbar.LENGTH_LONG)

        val textViewOfSnackBar: TextView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text)
        textViewOfSnackBar.setTextColor(Color.parseColor("#BB4444"))

        snackbar.show()
    }

    fun showSuccess(message: String, root: ViewGroup){
        val snackbar = Snackbar.make(root,message, Snackbar.LENGTH_LONG)

        val textViewOfSnackBar: TextView = snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text)
        textViewOfSnackBar.setTextColor(Color.parseColor("#00FF00"))

        snackbar.show()
    }

    fun setSuccess(textView: TextView, message:String){
        textView.text = message
        textView.setTextColor(Color.parseColor("#00FF00"))
    }

    fun setError(textView: TextView, message: String){
        textView.text = message
        textView.setTextColor(Color.parseColor("#BB4444"))
    }
}