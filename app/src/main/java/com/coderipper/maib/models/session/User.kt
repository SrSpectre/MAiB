package com.coderipper.maib.models.session

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "users",
    indices = [Index(value = ["phone", "email"], unique = true)]
)
data class User(
    @PrimaryKey val id: Long = System.nanoTime(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "last") val last: String,
    @ColumnInfo(name = "description") val description: String = "¡Hola, soy nuevo!",
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String,
    @ColumnInfo(name = "followers") val followers: Int = 0,
    @ColumnInfo(name = "rate") val rate: Float = 1F,
    @ColumnInfo(name = "avatar") val avatar: Int,
)
