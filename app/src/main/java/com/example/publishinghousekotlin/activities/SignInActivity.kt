package com.example.publishinghousekotlin.activities

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.publishinghousekotlin.R;
import com.example.publishinghousekotlin.basics.Validator
import com.example.publishinghousekotlin.http.requests.LoginRequest
import com.example.publishinghousekotlin.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignInActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailText = findViewById<EditText>(R.id.emailText)
        val passwordText = findViewById<EditText>(R.id.passwordText)
        val signInBtn = findViewById<Button>(R.id.signInBtn)
        val textView = findViewById<TextView>(R.id.textView2)

        signInBtn.setOnClickListener {

            val email = emailText.text.toString()
            val password = passwordText.text.toString()

            val loginRequest = LoginRequest(emailText.text.toString(), passwordText.text.toString())
            val userRepository = UserRepository(applicationContext.resources.getString(R.string.server_url))
            try {
               GlobalScope.launch(Dispatchers.IO) {
                   //textView.text = userRepository.signin(loginRequest)!!.user.email
               }
            }
            catch (e: Exception){
                Log.e("ошибка", "Произошла ошибка", e)
            }
        }

    }
}