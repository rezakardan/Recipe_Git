package com.example.recipefood.data.repository


import com.example.recipefood.data.source.RecipeLocal
import com.example.recipefood.data.source.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RecipeRepository @Inject constructor(
     remoteDataSource: RemoteDataSource,
     localDataSource: RecipeLocal
) {

val remote=remoteDataSource

    val local=localDataSource








}