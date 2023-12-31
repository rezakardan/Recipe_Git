package com.example.recipefood.data.database

import androidx.room.TypeConverter
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.google.gson.Gson

class RecipeTypeConverter {

    val gson=Gson()


    @TypeConverter
    fun recipeToJson(recipe:ResponseRecipes):String{

        return gson.toJson(recipe)
    }



    @TypeConverter
    fun stringToRecipe(data:String):ResponseRecipes{

        return gson.fromJson(data,ResponseRecipes::class.java)

    }



    @TypeConverter
    fun detailToJson(data:ResponseDetail):String{

        return gson.toJson(data)



    }

    @TypeConverter
    fun stringToDetail(data: String):ResponseDetail{


        return gson.fromJson(data,ResponseDetail::class.java)


    }




}