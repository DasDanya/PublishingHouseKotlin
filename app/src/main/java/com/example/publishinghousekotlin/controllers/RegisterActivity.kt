package com.example.publishinghousekotlin.controllers


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.basics.Listener
import com.example.publishinghousekotlin.basics.Messages
import com.example.publishinghousekotlin.databinding.ActivityRegisterBinding
import com.example.publishinghousekotlin.http.requests.RegisterRequest
import com.example.publishinghousekotlin.http.responses.RegisterResponse
import com.example.publishinghousekotlin.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegisterActivity: AppCompatActivity() {

    private lateinit var activityRegisterBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(activityRegisterBinding.root)

        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)

        setListeners()

        activityRegisterBinding.registerBtn.setOnClickListener {
            register()
        }

    }

    private fun setListeners(){
        val listener = Listener()

        listener.usernameListener(activityRegisterBinding.nameText, activityRegisterBinding.nameContainer)
        listener.phoneListener(activityRegisterBinding.phoneText, activityRegisterBinding.phoneContainer)
        listener.emailListener(activityRegisterBinding.emailText, activityRegisterBinding.emailContainer)
        listener.passwordAndConfirmPasswordListener(activityRegisterBinding.passwordText, activityRegisterBinding.confirmPasswordText, activityRegisterBinding.passwordContainer, activityRegisterBinding.confirmPasswordContainer)
        listener.confirmPasswordListener(activityRegisterBinding.passwordText,activityRegisterBinding.confirmPasswordText, activityRegisterBinding.confirmPasswordContainer)
    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun register(){
        val message = Messages()

        if(activityRegisterBinding.nameContainer.helperText == null && activityRegisterBinding.phoneContainer.helperText == null && activityRegisterBinding.emailContainer.helperText == null &&
            activityRegisterBinding.passwordContainer.helperText == null && activityRegisterBinding.confirmPasswordContainer.helperText == null){

            val name = activityRegisterBinding.nameText.text.toString().trim()
            val phone = activityRegisterBinding.phoneText.text.toString().trim()
            val email = activityRegisterBinding.emailText.text.toString().trim()
            val password = activityRegisterBinding.passwordText.text.toString().trim()

            val registerRequest = RegisterRequest(name, phone, email,password, "CUSTOMER")
            var registerResponse: RegisterResponse? = null

            lifecycleScope.launch(Dispatchers.IO) {
                try{
                    delay(1000)
                    withContext(Dispatchers.Main){
                        activityRegisterBinding.progressBar.visibility = View.VISIBLE
                    }

                    registerResponse = UserRepository().register(registerRequest)

                } catch (e:Exception){
                    message.showError("Ошибка регистрации. Повторите попытку",activityRegisterBinding.root)
                }
                runOnUiThread{
                    activityRegisterBinding.progressBar.visibility = View.GONE
                }

                if(registerResponse != null){
                    if(registerResponse!!.code == 400){
                        message.showError(registerResponse!!.message, activityRegisterBinding.root)
                    }else{
                        message.showSuccess(registerResponse!!.message, activityRegisterBinding.root)

                        delay(1000)
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)


                    }
                }
            }

        }else{
            message.showError("Некорректный ввод. Повторите попытку.", activityRegisterBinding.root)
        }
    }

}