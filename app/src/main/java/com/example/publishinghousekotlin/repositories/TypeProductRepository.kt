package com.example.publishinghousekotlin.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.publishinghousekotlin.MyApplication
import com.example.publishinghousekotlin.R
import com.example.publishinghousekotlin.http.JwtInterceptor
import com.example.publishinghousekotlin.models.TypeProduct
import com.example.publishinghousekotlin.pagination.TypeProductsDataSource
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class TypeProductRepository {

    private val client = OkHttpClient.Builder().addInterceptor(JwtInterceptor()).build()
    private val gson = GsonBuilder().create()
    private val serverUrl = MyApplication.instance.applicationContext.resources.getString(R.string.server)


    suspend fun get(page:Int):List<TypeProduct>?{

        val request = Request.Builder()
            .url("$serverUrl/api/typeProducts?page=$page")
            .build()

        val response = client.newCall(request).execute()

        if(response.isSuccessful){
            val typeOfData = object : TypeToken<List<TypeProduct>>() {}.type
            return gson.fromJson(response.body?.string(), typeOfData);
        }

        return null;
    }

    fun getPagedTypeProducts() = Pager(
        config = PagingConfig(pageSize = 7, enablePlaceholders = false),
        pagingSourceFactory = {TypeProductsDataSource()}
    ).liveData

    suspend fun add(typeProduct: TypeProduct): Int{

        val typeProductAsJson = gson.toJson(typeProduct)
        val mediaType = "application/json; chapset=utf-8".toMediaType()
        val body = typeProductAsJson.toRequestBody(mediaType)

        val request = Request.Builder()
            .url("$serverUrl/api/typeProducts/add")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return response.code
    }


//    fun getPagedTypeProducts(): Flow<PagingData<TypeProduct>> {
//        val pageSize = 5
//        return Pager(
//            config = PagingConfig(
//                pageSize = pageSize,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {TypeProductsDataSource(pageSize)}
//        ).flow
//    }


}