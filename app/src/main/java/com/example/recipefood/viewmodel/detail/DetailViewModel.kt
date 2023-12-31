package com.example.recipefood.viewmodel.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipefood.data.database.entity.DetailEntity
import com.example.recipefood.data.database.entity.FavoriteEntity
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.data.model.detail.ResponseSimilar
import com.example.recipefood.data.repository.RecipeRepository
import com.example.recipefood.utils.MyResponseNetworkRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel@Inject constructor( private val repository: RecipeRepository):ViewModel() {


    val detailsLiveData = MutableLiveData<MyResponseNetworkRequest<ResponseDetail>>()
    fun getDetailsData(id: Int, apiKey: String) = viewModelScope.launch {
        detailsLiveData.value = MyResponseNetworkRequest.LOADING()

        val response = repository.remote.getDetailsData(id, apiKey)



        when {


            response.message().contains("timeout") -> detailsLiveData.value = MyResponseNetworkRequest.ERROR(message = "Timeout")
            response.code() == 401 -> detailsLiveData.value = MyResponseNetworkRequest.ERROR(message = "You are not authorized")
            response.code() == 402 -> detailsLiveData.value = MyResponseNetworkRequest.ERROR(message = "Your free plan finished")
            response.code() == 422 -> detailsLiveData.value = MyResponseNetworkRequest.ERROR(message = "Api key not found!")
            response.code() == 500 -> detailsLiveData.value = MyResponseNetworkRequest.ERROR(message = "Try again")
            response.body()!!.analyzedInstructions.isNullOrEmpty() -> detailsLiveData.value=MyResponseNetworkRequest.ERROR(message ="Not found any recipe!")
            response.body()!!.extendedIngredients.isNullOrEmpty() -> detailsLiveData.value=MyResponseNetworkRequest.ERROR(message ="Not found any recipe!")

            response.isSuccessful -> detailsLiveData.value = MyResponseNetworkRequest.SUCCESS(response.body()!!)
            else -> detailsLiveData.value =  MyResponseNetworkRequest.ERROR(message = response.message())


        }







val cache=detailsLiveData.value?.data

                if (cache!=null){

                    offlineSaveDetail(cache.id!!,cache)


                }












    }





        private fun saveDetail(entity: DetailEntity)=viewModelScope.launch {


             repository.local.saveDetail(entity)




         }



    private fun offlineSaveDetail(id:Int,response:ResponseDetail){

        val entity=DetailEntity(id,response)

        saveDetail(entity)




    }



    fun readDetailData(id: Int)=repository.local.getDetailData(id).asLiveData()




    val existLiveData=MutableLiveData<Boolean>()
    fun existDetailData(id: Int)=viewModelScope.launch {




        repository.local.existDetail(id).collect{


            existLiveData.postValue(it)




        }






    }






 fun saveFavorites(entity: FavoriteEntity)=viewModelScope.launch {


    repository.local.saveFavorite(entity)




}


    fun deleteFavorite(entity: FavoriteEntity)=viewModelScope.launch {



        repository.local.deleteFavorite(entity)
    }




    val existFavorite=MutableLiveData<Boolean>()
    fun existFavorite(id:Int)=viewModelScope.launch{


        repository.local.existFavorite(id).collect{

            if (it){

existFavorite.postValue(it)

            }




        }



    }





















    val similarLiveData = MutableLiveData<MyResponseNetworkRequest<ResponseSimilar>>()
    fun getSimilarRecipes(id: Int,apiKey: String ) = viewModelScope.launch {
        similarLiveData.value=MyResponseNetworkRequest.LOADING()
        val response = repository.remote.getSimilarRecipes(id, apiKey)




        when  {


            response.message().contains("timeout") -> similarLiveData.value=MyResponseNetworkRequest.ERROR(message ="Timeout")
            response.code() == 401 -> similarLiveData.value=MyResponseNetworkRequest.ERROR(message = "You are not authorized")
            response.code() == 402 ->similarLiveData.value=MyResponseNetworkRequest.ERROR(message ="Your free plan finished")
            response.code() == 422 -> similarLiveData.value=MyResponseNetworkRequest.ERROR(message ="Api key not found!")
            response.code() == 500 -> similarLiveData.value=MyResponseNetworkRequest.ERROR(message ="Try again")
            response.body().isNullOrEmpty() -> similarLiveData.value=MyResponseNetworkRequest.ERROR(message ="Not found any recipe!")

            response.isSuccessful -> similarLiveData.value=MyResponseNetworkRequest.SUCCESS(response.body()!!)
            else ->similarLiveData.value= MyResponseNetworkRequest.ERROR(message =response.message())

        }


    }
}

