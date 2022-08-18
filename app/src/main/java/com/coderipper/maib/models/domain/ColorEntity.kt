package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

data class ColorEntity(
    val id: String = UUID.randomUUID().toString(),
    /*@ColumnInfo(name = "red") val red: Int,
    @ColumnInfo(name = "green") val green: Int,
    @ColumnInfo(name = "blue") val blue: Int,
    @ColumnInfo(name = "id_product") val id_product: Long*/
    val color: Int = -1,
)
