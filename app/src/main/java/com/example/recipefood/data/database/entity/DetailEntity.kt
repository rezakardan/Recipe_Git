package com.example.recipefood.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.recipefood.data.model.detail.ResponseDetail
import com.example.recipefood.utils.Constants


@Entity(tableName = Constants.DETAIL_TABLE)
data class DetailEntity(

    @PrimaryKey(autoGenerate = false)
    val id:Int,

    val detailEntity:ResponseDetail






)
