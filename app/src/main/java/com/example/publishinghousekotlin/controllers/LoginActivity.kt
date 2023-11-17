package com.example.publishinghousekotlin.controllers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.basics.Listener
import com.example.publishinghousekotlin.basics.Messages
import com.example.publishinghousekotlin.databinding.ActivityLoginBinding
import com.example.publishinghousekotlin.http.requests.LoginRequest
import com.example.publishinghousekotlin.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity: AppCompatActivity() {

    private lateinit var activityLoginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)

        setListeners()

        activityLoginBinding.loginBtn.setOnClickListener {
            login()
        }

        activityLoginBinding.registerTextView.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setListeners(){
        val listener = Listener()

        listener.emailListener(activityLoginBinding.emailText, activityLoginBinding.emailContainer)
        listener.passwordListener(activityLoginBinding.passwordText, activityLoginBinding.passwordContainer)
    }
    private fun login(){
        val message = Messages()

        if(activityLoginBinding.emailContainer.helperText == null && activityLoginBinding.passwordContainer.helperText == null){

            val email = activityLoginBinding.emailText.text.toString()
            val password = activityLoginBinding.passwordText.text.toString()

            val loginRequest = LoginRequest(email, password)
            val userRepository = UserRepository(applicationContext.resources.getString(R.string.server))

            //activityLoginBinding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    delay(1000)
                    withContext(Dispatchers.Main){
                        activityLoginBinding.progressBar.visibility = View.VISIBLE
                    }

                    userRepository.login(loginRequest)
                    //activityLoginBinding.textView2.text = userRepository.login(loginRequest)!!.user.email
                    val messages = Messages()
                    messages.showSuccess(userRepository.login(loginRequest)!!.user.email,activityLoginBinding.root)
                } catch(e: Exception){
                    message.showError("Ошибка авторизации. Повторите попытку",activityLoginBinding.root)
                }
                runOnUiThread{
                    activityLoginBinding.progressBar.visibility = View.GONE
                }
            }

        } else{
            message.showError("Некорректный ввод. Повторите попытку.",activityLoginBinding.root)
        }
    }

}