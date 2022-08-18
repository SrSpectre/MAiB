package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "hashtag_product",
)
data class HashtagProduct(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "id_hashtag") val id_hashtag: String,
    @ColumnInfo(name = "id_product") val id_product: String,
)
