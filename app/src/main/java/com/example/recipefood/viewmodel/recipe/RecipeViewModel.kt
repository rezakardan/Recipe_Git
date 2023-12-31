package com.example.recipefood.viewmodel.recipe

import androidx.lifecycle.*
import com.example.recipefood.data.database.entity.RecipeEntity
import com.example.recipefood.data.model.recipe.ResponseRecipes
import com.example.recipefood.data.repository.MenuRepository
import com.example.recipefood.data.repository.RecipeRepository
import com.example.recipefood.utils.Constants
import com.example.recipefood.utils.MyResponseNetworkRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(private val repository: RecipeRepository,private val menuRepository: MenuRepository) : ViewModel() {


    fun popularQueries(): HashMap<String, String> {


        val queries: HashMap<String, String> = HashMap()


        queries[Constants.API_KEY] = Constants.MY_API_KEY

        queries[Constants.SORT] = Constants.POPULARITY
        queries[Constants.NUMBER] = Constants.LIMITED_COUNT.toString()
        queries[Constants.ADD_RECIPE_INFORMATION] = Constants.TRUE
        return queries

    }


    val recipeData = MutableLiveData<MyResponseNetworkRequest<ResponseRecipes>>()

    fun callRecipeFoodsApi(queries: Map<String, String>) =
        viewModelScope.launch {
            recipeData.value = MyResponseNetworkRequest.LOADING()

            val response = repository.remote.getRecipes(queries)

   when{






       response.message().contains("timeout") -> recipeData.value =MyResponseNetworkRequest.ERROR(message ="Timeout")
       response.code() == 401 -> recipeData.value =MyResponseNetworkRequest.ERROR(message = "You are not authorized")
       response.code() == 402 ->recipeData.value = MyResponseNetworkRequest.ERROR(message ="Your free plan finished")
       response.code() == 422 -> recipeData.value =MyResponseNetworkRequest.ERROR(message ="Api key not found!")
       response.code() == 500 -> recipeData.value =MyResponseNetworkRequest.ERROR(message ="Try again")
       response.isSuccessful -> recipeData.value =MyResponseNetworkRequest.SUCCESS(response.body()!!)
       else ->recipeData.value = MyResponseNetworkRequest.ERROR(message =response.message())




   }







            val cache = recipeData.value?.data

            if (cache != null) {

                offlinePopularRecipe(cache)


            }


        }





    private fun savePopularRecipe(entity: RecipeEntity)=viewModelScope.launch {


        repository.local.saveRecipe(entity)





    }



    private fun offlinePopularRecipe(response:ResponseRecipes){


        val entity= RecipeEntity(0,response)

        savePopularRecipe(entity)


    }



    val readAllPopularRecipes = repository.local.getAllRecipes().asLiveData()

























    private var mealType=Constants.MAIN_COURSE

    private var dietType=Constants.GLUTEN_FREE

fun recentQueries():HashMap<String,String> {

viewModelScope.launch {

    menuRepository.readMenuItems.collect{

        mealType=it.mealsType
        dietType=it.dietType



    }



}


    val queries:HashMap<String,String> = HashMap()


    queries[Constants.API_KEY]=Constants.MY_API_KEY

    queries[Constants.TYPE]= mealType

    queries[Constants.DIET]=dietType

    queries[Constants.NUMBER]=Constants.FULL_COUNT.toString()

    queries[Constants.ADD_RECIPE_INFORMATION]=Constants.TRUE

return queries

}





val recentLiveData=MutableLiveData<MyResponseNetworkRequest<ResponseRecipes>>()

  fun callRecentRecipesApi(queries: Map<String, String>)=viewModelScope.launch {


      recentLiveData.value=MyResponseNetworkRequest.LOADING()

      val response=repository.remote.getRecipes(queries)




      when{


          response.message().contains("timeout") -> recentLiveData.value=MyResponseNetworkRequest.ERROR(message ="Timeout")
          response.code() == 401 ->recentLiveData.value= MyResponseNetworkRequest.ERROR(message = "You are not authorized")
          response.code() == 402 -> recentLiveData.value=MyResponseNetworkRequest.ERROR(message ="Your free plan finished")
          response.code() == 422 -> recentLiveData.value=MyResponseNetworkRequest.ERROR(message ="Api key not found!")
          response.code() == 500 -> recentLiveData.value=MyResponseNetworkRequest.ERROR(message ="Try again")
          response.body()!!.results.isNullOrEmpty()->recentLiveData.value=MyResponseNetworkRequest.ERROR(message = "Your Request is Empty")
          response.isSuccessful -> recentLiveData.value=MyResponseNetworkRequest.SUCCESS(response.body()!!)
          else -> recentLiveData.value=MyResponseNetworkRequest.ERROR(message =response.message())







      }



      val recentCache=recentLiveData.value?.data

      if (recentCache!=null){

          offlineRecentRecipe(recentCache)


      }



  }


























   private fun saveRecentFoods(entity: RecipeEntity)=viewModelScope.launch {

        repository.local.saveRecipe(entity)



    }



    private fun offlineRecentRecipe(response: ResponseRecipes){


        val entity= RecipeEntity(1,response)

        saveRecentFoods(entity)


    }


    val readRecentRecipes=repository.local.getAllRecipes().asLiveData()











}