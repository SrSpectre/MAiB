package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "cart",
)
data class Cart(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "product_id") val productId: String,
)
