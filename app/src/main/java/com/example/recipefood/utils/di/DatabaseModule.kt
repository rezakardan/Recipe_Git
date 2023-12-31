package com.example.recipefood.utils.di

import android.content.Context
import androidx.room.Room
import com.example.recipefood.data.database.RecipeDatabase
import com.example.recipefood.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {



    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)=Room.databaseBuilder(context,RecipeDatabase::class.java,Constants.DATABASE_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideDao(db:RecipeDatabase)=db.recipeDao()




}