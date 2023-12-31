package com.example.recipefood.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.recipefood.data.database.entity.DetailEntity
import com.example.recipefood.data.database.entity.FavoriteEntity
import com.example.recipefood.data.database.entity.RecipeEntity

@Database(entities = [RecipeEntity::class,DetailEntity::class,FavoriteEntity::class], version =4, exportSchema = false)
@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase:RoomDatabase() {


    abstract fun recipeDao():RecipeDao


}