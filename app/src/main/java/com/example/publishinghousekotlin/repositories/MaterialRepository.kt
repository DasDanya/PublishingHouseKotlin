package com.example.publishinghousekotlin.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.publishinghousekotlin.MyApplication
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.http.JwtInterceptor
import com.example.publishinghousekotlin.http.responses.MessageResponse
import com.example.publishinghousekotlin.models.Material
import com.example.publishinghousekotlin.models.TypeProduct
import com.example.publishinghousekotlin.pagination.MaterialsDataSource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class MaterialRepository {

    private val client = OkHttpClient.Builder().addInterceptor(JwtInterceptor()).build()
    private val gson = GsonBuilder().create()
    private val serverUrl = MyApplication.instance.applicationContext.resources.getString(R.string.server)

    suspend fun get(page: Int, type:String):List<Material>?{
        var url = "$serverUrl/api/materials?page=$page"
        if(type != ""){
            url += "&type=$type"
        }

        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        if(response.isSuccessful){
            val typeOfData = object : TypeToken<List<Material>>() {}.type
            return gson.fromJson(response.body?.string(), typeOfData);
        }

        return null;
    }

    fun getPagedMaterials(type:String) = Pager(
        config = PagingConfig(pageSize = 7, enablePlaceholders = false),
        pagingSourceFactory = {MaterialsDataSource(type)}
    ).liveData


    suspend fun delete(materialId:Long): MessageResponse?{
        val request = Request.Builder()
            .url("$serverUrl/api/materials/delete/$materialId")
            .delete()
            .build()

        val response = client.newCall(request).execute()

        if(response.code == 409 || response.code == 204){
            return MessageResponse(response.code, response.body!!.string())
        }

        return null
    }

    suspend fun add(material:Material): Int{
        val materialAsJson = gson.toJson(material)
        val mediaType = "application/json; chapset=utf-8".toMediaType()
        val body = materialAsJson.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$serverUrl/api/materials/add")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return response.code
    }

    suspend fun update(material: Material): Int{
        val materialAsJson = gson.toJson(material)
        val mediaType = "application/json; chapset=utf-8".toMediaType()
        val body = materialAsJson.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$serverUrl/api/materials/update/${material.id}")
            .put(body)
            .build()

        val response = client.newCall(request).execute()

        return response.code
    }
}