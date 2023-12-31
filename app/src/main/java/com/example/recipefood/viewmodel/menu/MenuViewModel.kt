package com.example.recipefood.viewmodel.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefood.data.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel@Inject constructor(private val repository: MenuRepository):ViewModel() {


    fun saveMenuItems(mealsType:String,mealsId:Int,dietType:String,dietId:Int)=viewModelScope.launch {

        repository.saveMenuItems(mealsType,mealsId, dietType, dietId)




    }



    val readMenuItems= repository.readMenuItems





    fun mealsList(): MutableList<String> {
        return mutableListOf(
            "Main Course", "Bread", "Marinade", "Side Dish", "Breakfast", "Dessert", "Soup", "Snack", "Appetizer",
            "Beverage", "Drink", "Salad", "Sauce"
        )
    }

    fun dietsList(): MutableList<String> {
        return mutableListOf("Gluten Free", "Ketogenic", "Vegetarian", "Vegan", "Pescetarian", "Paleo")
    }



}