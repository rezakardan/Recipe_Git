package com.example.recipefood.data.source

import com.example.recipefood.data.database.RecipeDao
import com.example.recipefood.data.database.entity.DetailEntity
import com.example.recipefood.data.database.entity.FavoriteEntity
import com.example.recipefood.data.database.entity.RecipeEntity
import javax.inject.Inject

class RecipeLocal@Inject constructor(private val dao:RecipeDao) {


    suspend fun saveRecipe(entity: RecipeEntity)=dao.saveRecipe(entity)

    fun getAllRecipes()=dao.getAllRecipes()


  suspend fun saveDetail(entity: DetailEntity)=dao.saveDetailsData(entity)

    fun getDetailData(id:Int)=dao.getDetailData(id)

    fun existDetail(id: Int)=dao.existsDetailData(id)






    suspend fun saveFavorite(entity: FavoriteEntity)=dao.saveFavorite(entity)

    suspend fun deleteFavorite(entity: FavoriteEntity)=dao.deleteFavorite(entity)

    fun getAllFavorite()=dao.getAllFavorites()

    fun existFavorite(id:Int)=dao.existFavorite(id)









}