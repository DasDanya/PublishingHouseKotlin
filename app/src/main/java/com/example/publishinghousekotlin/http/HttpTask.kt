package com.example.publishinghousekotlin.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HttpTask:Thread(){

    var response: Response? = null

    fun execute(request: Request){
        val thread = Thread{
            val client = OkHttpClient()

            try{
                val response = client.newCall(request).execute()
                if(response.isSuccessful){
                    this.response = response
                } else{
                    throw IOException("Ошибка выполнения http-запроса")
                }
            } catch (e:Exception){
                Log.e("Ошибка", e.message!!)
            }
        }

        thread.start()
        thread.join()
    }
}