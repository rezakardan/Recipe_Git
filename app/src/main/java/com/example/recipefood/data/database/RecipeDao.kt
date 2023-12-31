package com.example.recipefood.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.recipefood.data.database.entity.DetailEntity
import com.example.recipefood.data.database.entity.FavoriteEntity
import com.example.recipefood.data.database.entity.RecipeEntity
import com.example.recipefood.utils.Constants
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipeDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveRecipe(entity: RecipeEntity)


    @Query("SELECT*FROM ${Constants.RECIPE_TABLE_NAME} ORDER BY ID ASC")
    fun getAllRecipes():Flow<List<RecipeEntity>>




    @Insert(onConflict = REPLACE)
    suspend fun saveDetailsData(entity: DetailEntity)


    @Query("SELECT*FROM ${Constants.DETAIL_TABLE} WHERE id= :id")
    fun getDetailData(id:Int):Flow<DetailEntity>




    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.DETAIL_TABLE} WHERE id= :id)")
    fun existsDetailData(id: Int):Flow<Boolean>






    @Insert(onConflict = REPLACE)
    suspend fun saveFavorite(entity:FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(entity: FavoriteEntity)


    @Query("SELECT*FROM ${Constants.FAVORITE_TABLE} ORDER BY ID ASC")
    fun getAllFavorites():Flow<List<FavoriteEntity>>



    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.FAVORITE_TABLE} WHERE id=:id)")
    fun existFavorite(id:Int):Flow<Boolean>











}