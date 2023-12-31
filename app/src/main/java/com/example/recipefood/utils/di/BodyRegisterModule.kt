package com.example.recipefood.utils.di

import com.example.recipefood.data.model.register.BodyRegister
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(FragmentComponent::class)
object BodyRegisterModule {


    @Provides
    fun provideBodyRegister()=BodyRegister()



}