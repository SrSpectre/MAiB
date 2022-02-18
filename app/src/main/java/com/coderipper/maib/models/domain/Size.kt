package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sizes",
)
data class Size(
    @PrimaryKey val id: Long = System.nanoTime(),
    @ColumnInfo(name = "size") val size: Int,
    //@ColumnInfo(name = "id_product") val id_product: Long
)
