package com.example.recipefood.data.repository

import com.example.recipefood.data.source.RecipeLocal
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FavoriteRepository@Inject constructor(localSource:RecipeLocal) {


    val local=localSource
}