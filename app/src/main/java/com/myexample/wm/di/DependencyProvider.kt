package com.myexample.wm.di

import com.myexample.wm.Constants
import com.myexample.wm.data.WalmartAPI
import com.myexample.wm.data.repository.WalmartRepoImpl
import com.myexample.wm.domain.repository.WalmartRepo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DependencyProvider {

    private val api: WalmartAPI by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        Retrofit.Builder()
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WalmartAPI::class.java)
    }


    val repo: WalmartRepo by lazy {
        WalmartRepoImpl(api)
    }
}