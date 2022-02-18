package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hashtag",
)
data class Hashtag(
    @PrimaryKey val id: Long = System.nanoTime(),
    @ColumnInfo(name = "name") val name: String,
)
