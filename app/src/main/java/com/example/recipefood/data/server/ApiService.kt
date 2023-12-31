package com.example.recipefood.data.server

import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.data.model.detail.ResponseSimilar
import com.example.recipefood.data.model.lucky.ResponseLucky
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.data.model.register.BodyRegister
import com.example.recipefood.data.model.register.ResponseRegister
import com.example.recipefood.utils.Constants
import com.example.recipefood.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {


@POST("users/connect")
suspend fun postRegister(@Query(API_KEY) apiKey:String, @Body body:BodyRegister):Response<ResponseRegister>






@GET("recipes/complexSearch")
suspend fun getRecipeFoods(@QueryMap queries: Map<String,String>):Response<ResponseRecipes>







@GET("recipes/{id}/information")
suspend fun getDetailsData(@Path("id")id:Int,@Query(API_KEY) apiKey:String):Response<ResponseDetail>





@GET("recipes/{id}/similar")
suspend fun getSimilarRecipes(@Path("id")id:Int,@Query(Constants.API_KEY)apiKey:String):Response<ResponseSimilar>



@GET("recipes/random")
suspend fun luckyFood(@QueryMap queries:Map<String,String>):Response<ResponseLucky>



}