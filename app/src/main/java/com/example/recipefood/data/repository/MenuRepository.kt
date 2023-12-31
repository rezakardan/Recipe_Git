package com.example.recipefood.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipefood.data.model.menu.MenuStoreItemsResponse
import com.example.recipefood.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class MenuRepository @Inject constructor(@ApplicationContext val context: Context) {


    private object MenuDataStoreKeys {

        val mealsType = stringPreferencesKey(Constants.MEALS_TITLE)

        val mealsId = intPreferencesKey(Constants.MEALS_ID)

        val dietType = stringPreferencesKey(Constants.DIET_TITLE)

        val dietId = intPreferencesKey(Constants.DIET_ID)


    }


   private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(Constants.MENU_STORE_INFO)


    suspend fun saveMenuItems(mealsType: String, mealsId: Int, dietType: String, dietId: Int) =
        context.dataStore.edit {

            it[MenuDataStoreKeys.mealsType]=mealsType
            it[MenuDataStoreKeys.mealsId]=mealsId

            it[MenuDataStoreKeys.dietType]=dietType

            it[MenuDataStoreKeys.dietId]=dietId

        }





    val readMenuItems:Flow<MenuStoreItemsResponse> = context.dataStore.data
        .catch { e->

            if (e is IOException){
                emit(emptyPreferences())
            }else{
                throw e
            }



        }.map {

            val mealsTitle=it[MenuDataStoreKeys.mealsType]?:"main course"
            val mealsId=it[MenuDataStoreKeys.mealsId]?:0

            val dietTitle=it[MenuDataStoreKeys.dietType]?:"Gluten Free"
            val dietId=it[MenuDataStoreKeys.dietId]?:0


            MenuStoreItemsResponse(mealsTitle,mealsId,dietTitle,dietId)

        }






}