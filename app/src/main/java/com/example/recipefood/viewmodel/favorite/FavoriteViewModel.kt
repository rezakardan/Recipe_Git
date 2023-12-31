package com.example.recipefood.viewmodel.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.recipefood.data.database.entity.FavoriteEntity
import com.example.recipefood.data.repository.FavoriteRepository
import com.example.recipefood.data.source.RecipeLocal
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel@Inject constructor(  repository:FavoriteRepository):ViewModel() {


    val allFavorites= repository.local.getAllFavorite().asLiveData()















}