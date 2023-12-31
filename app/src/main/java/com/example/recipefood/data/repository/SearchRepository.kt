package com.example.recipefood.data.repository

import com.example.recipefood.data.source.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject


@ActivityRetainedScoped
class SearchRepository@Inject constructor(private val remote:RemoteDataSource) {


    suspend fun searchRecipes(queries:Map<String,String>)=remote.getRecipes(queries)





}