package com.example.recipefood.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.recipefood.data.model.register.BodyRegister
import com.example.recipefood.data.model.register.RegisterModelDataStore
import com.example.recipefood.data.source.RemoteDataSource
import com.example.recipefood.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.IOException

import javax.inject.Inject

@ActivityRetainedScoped
class RegisterRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerRepo: RemoteDataSource
) {


   object DataStoreKeys{

       val userName= stringPreferencesKey(Constants.USER_NAME_STORE)
       val hash= stringPreferencesKey(Constants.HASH_STORE)


   }


    private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(Constants.REGISTER_STORE_INFO)

    suspend fun saveDataStore( userName:String,hash:String)=context.dataStore.edit {

        it[DataStoreKeys.userName]=userName
        it[DataStoreKeys.hash]=hash





    }


     val readDataStore:Flow<RegisterModelDataStore> = context.dataStore.data
         .catch { e->

            if (e is IOException){

                emit(emptyPreferences())

            }else{

                throw e

            }





        }.map {
             val userName=it[DataStoreKeys.userName]?:""
            val hash=it[DataStoreKeys.hash]?:""
             RegisterModelDataStore(userName, hash) }






    suspend fun registerRepository(apiKey: String, body: BodyRegister) =
        registerRepo.postRegister(apiKey, body)






}