package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hashtag_product",
)
data class HashtagProduct(
    @PrimaryKey val id: Long = System.nanoTime(),
    @ColumnInfo(name = "id_hashtag") val id_hashtag: Long,
    @ColumnInfo(name = "id_product") val id_product: Long,
)
