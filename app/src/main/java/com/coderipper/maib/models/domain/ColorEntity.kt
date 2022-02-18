package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "colors",
)
data class ColorEntity(
    @PrimaryKey val id: Long = System.nanoTime(),
    /*@ColumnInfo(name = "red") val red: Int,
    @ColumnInfo(name = "green") val green: Int,
    @ColumnInfo(name = "blue") val blue: Int,
    @ColumnInfo(name = "id_product") val id_product: Long*/
    @ColumnInfo(name = "color") val color: Int,
)
