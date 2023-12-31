package com.example.recipefood.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.utils.Constants


@Entity(tableName = Constants.FAVORITE_TABLE)
data class FavoriteEntity (


    @PrimaryKey(autoGenerate = false)
    val id:Int,

    val favoriteResponse:ResponseDetail


        )