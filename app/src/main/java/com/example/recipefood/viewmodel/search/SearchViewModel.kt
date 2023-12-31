package com.example.recipefood.viewmodel.search

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefood.data.model.lucky.ResponseLucky
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.data.repository.LuckyRepository
import com.example.recipefood.data.repository.SearchRepository
import com.example.recipefood.utils.Constants
import com.example.recipefood.utils.MyResponseNetworkRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(private val repository: SearchRepository):ViewModel() {




    fun searchQueries(search:String):HashMap<String,String>{


val queries:HashMap<String,String> = HashMap()


        queries[Constants.API_KEY]=Constants.MY_API_KEY

        queries[Constants.NUMBER]=Constants.FULL_COUNT.toString()
        queries[Constants.QUERY]=search

        queries[Constants.ADD_RECIPE_INFORMATION]=Constants.TRUE
        return queries

    }



val searchLiveData=MutableLiveData<MyResponseNetworkRequest<ResponseRecipes>>()

    fun callSearchApi(queries:Map<String,String>)=viewModelScope.launch {

val response=repository.searchRecipes(queries)

        searchLiveData.value=  MyResponseNetworkRequest.LOADING()
        
        when(response.code()){
            
            
            
            in 200..202->{




                searchLiveData.value=   MyResponseNetworkRequest.SUCCESS(response.body()!!)




            }


            401 -> {
                searchLiveData.value =
                    MyResponseNetworkRequest.ERROR(message = "You are not authorized")
            }


            402 -> {
                searchLiveData.value =
                    MyResponseNetworkRequest.ERROR(message = "Your free plan finished")
            }
            422 -> {
                searchLiveData.value =
                    MyResponseNetworkRequest.ERROR(message = "Api key not found!")
            }
            500 -> {
                searchLiveData.value = MyResponseNetworkRequest.ERROR(message = "Try again")
            }


            else -> {
                searchLiveData.value = MyResponseNetworkRequest.ERROR(message = response.message())
            }
            
            
            
            
            
            
        }
        
        










    }




}