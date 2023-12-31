package com.example.recipefood.viewmodel.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefood.data.model.register.BodyRegister
import com.example.recipefood.data.model.register.ResponseRegister
import com.example.recipefood.data.repository.RegisterRepository
import com.example.recipefood.utils.MyResponseNetworkRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel@Inject constructor(private val repository: RegisterRepository):ViewModel() {

val registerData=MutableLiveData<MyResponseNetworkRequest<ResponseRegister>>()


    fun register( apiKey:String,body:BodyRegister)=viewModelScope.launch {

        val response=repository.registerRepository(apiKey, body)



        registerData.value=MyResponseNetworkRequest.LOADING()
        when(response.code()){


            in 200..202->{

                registerData.value=MyResponseNetworkRequest.SUCCESS(response.body()!!)




            }


            401->registerData.value=MyResponseNetworkRequest.ERROR(message = "you are not authorized")


            402->registerData.value=MyResponseNetworkRequest.ERROR(message = "your free plan finished")

            422->registerData.value=MyResponseNetworkRequest.ERROR(message = "Api Key not found!")

            500->registerData.value=MyResponseNetworkRequest.ERROR(message = "Try Again!")
            in 400..499->{



                registerData.value=MyResponseNetworkRequest.ERROR(message = response.message().toString())


            }

            in 500..599->{

                registerData.value=MyResponseNetworkRequest.ERROR(message = response.message().toString())
            }




        }





    }



fun saveDataStore(userName:String,hash:String)=viewModelScope.launch {


    repository.saveDataStore(userName, hash)




}



    val readDataStore=repository.readDataStore












}