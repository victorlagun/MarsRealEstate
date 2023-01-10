package com.example.android.marsrealestate.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mars_property")
data class MarsProperty(
    @PrimaryKey
    val id: String,
    val price: Long,
    val type: String,
    @ColumnInfo(name = "img_src")
    val imgSrc: String,
)