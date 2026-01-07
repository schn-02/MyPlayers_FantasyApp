package com.example.myplayer.Retrofit

import com.example.myplayer.Interceptor.FirebaseInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient
{
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(FirebaseInterceptor())
        .build()


    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .baseUrl("http://10.0.2.2:9090/")
        .build()

    val AuthData = retrofit.create(RetrofitInterface::class.java)

}