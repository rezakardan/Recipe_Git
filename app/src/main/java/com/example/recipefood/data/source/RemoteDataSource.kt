package com.example.recipefood.data.source

import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.data.model.register.BodyRegister
import com.example.recipefood.data.server.ApiService
import retrofit2.Response
import retrofit2.http.Query
import javax.inject.Inject

class RemoteDataSource@Inject constructor(private val api:ApiService) {

    suspend fun postRegister(apiKey:String,body:BodyRegister)=api.postRegister(apiKey, body)


    suspend fun getRecipes(queries: Map<String,String>)=api.getRecipeFoods(queries)


suspend fun getDetailsData(id:Int,apiKey:String)=api.getDetailsData(id,apiKey)

    suspend fun getSimilarRecipes(id:Int,apiKey:String)=api.getSimilarRecipes(id, apiKey)


    suspend fun getRandomRecipes(queries:Map<String,String>)=api.luckyFood(queries)



}