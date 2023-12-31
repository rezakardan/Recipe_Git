package com.example.recipefood.data.repository

import com.example.recipefood.data.source.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class LuckyRepository@Inject constructor(val remoteDataSource:RemoteDataSource) {


   suspend fun getRandomRecipe(queries:Map<String,String>)= remoteDataSource.getRandomRecipes(queries)












}