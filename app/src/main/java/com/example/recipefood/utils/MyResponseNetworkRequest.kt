package com.example.recipefood.utils

sealed class MyResponseNetworkRequest<T>( val data:T?=null,val error:String?=null) {



    class LOADING<T>:MyResponseNetworkRequest<T>()

    class SUCCESS<T>(data: T):MyResponseNetworkRequest<T>(data)

    class ERROR<T>(data: T?=null,message:String):MyResponseNetworkRequest<T>(data,message)










}