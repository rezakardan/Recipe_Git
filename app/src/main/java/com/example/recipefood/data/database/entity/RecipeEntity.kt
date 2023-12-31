package com.example.recipefood.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.utils.Constants


@Entity(tableName = Constants.RECIPE_TABLE_NAME)
data class RecipeEntity(


    @PrimaryKey(autoGenerate = false)
    var id:Int=0,


    var foodRecipe:ResponseRecipes





)