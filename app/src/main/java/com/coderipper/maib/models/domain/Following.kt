package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "following",
)
data class Following(
    @PrimaryKey val id: Long = System.nanoTime(),
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "follower_id") val followerId: Long,
)
