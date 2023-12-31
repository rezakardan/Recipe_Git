package com.example.recipefood.utils.di

import com.example.recipefood.data.server.ApiService
import com.example.recipefood.utils.Constants.BASE_URL
import com.example.recipefood.utils.Constants.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl()=BASE_URL


    @Provides
    @Singleton
    fun timeOutConnection()=NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideBodyInterceptor()=HttpLoggingInterceptor().apply {

        level=HttpLoggingInterceptor.Level.BODY



    }


    @Provides
    @Singleton
    fun provideGson():Gson=GsonBuilder().setLenient().create()




    @Provides
    @Singleton
    fun provideClient(time:Long,body:HttpLoggingInterceptor)=OkHttpClient.Builder()
        .writeTimeout(time,TimeUnit.SECONDS)
        .readTimeout(time,TimeUnit.SECONDS)
        .connectTimeout(time,TimeUnit.SECONDS)
        .addInterceptor(body)
        .retryOnConnectionFailure(true)
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(url:String,client:OkHttpClient,gson:Gson):ApiService=Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiService::class.java)






}