package com.example.recipefood.viewmodel.lucky

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefood.data.model.lucky.ResponseLucky
import com.example.recipefood.data.repository.LuckyRepository
import com.example.recipefood.utils.Constants
import com.example.recipefood.utils.MyResponseNetworkRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LuckyViewModel@Inject constructor(private val repository: LuckyRepository):ViewModel() {




    fun luckyQueries():HashMap<String,String>{


val queries:HashMap<String,String> = HashMap()


        queries[Constants.API_KEY]=Constants.MY_API_KEY

        queries[Constants.NUMBER]="1"

        queries[Constants.ADD_RECIPE_INFORMATION]=Constants.TRUE
        return queries

    }



val luckyLiveData=MutableLiveData<MyResponseNetworkRequest<ResponseLucky>>()
    @SuppressLint("SuspiciousIndentation")
    fun callLuckyApi(queries:Map<String,String>)=viewModelScope.launch {

val response=repository.remoteDataSource.getRandomRecipes(queries)
        
     luckyLiveData.value=  MyResponseNetworkRequest.LOADING()
        
        when(response.code()){
            
            
            
            in 200..202->{




                luckyLiveData.value=   MyResponseNetworkRequest.SUCCESS(response.body()!!)




            }


            401 -> {
                luckyLiveData.value =
                    MyResponseNetworkRequest.ERROR(message = "You are not authorized")
            }


            402 -> {
                luckyLiveData.value =
                    MyResponseNetworkRequest.ERROR(message = "Your free plan finished")
            }
            422 -> {
                luckyLiveData.value =
                    MyResponseNetworkRequest.ERROR(message = "Api key not found!")
            }
            500 -> {
                luckyLiveData.value = MyResponseNetworkRequest.ERROR(message = "Try again")
            }


            else -> {
                luckyLiveData.value = MyResponseNetworkRequest.ERROR(message = response.message())
            }
            
            
            
            
            
            
        }
        
        










    }




}