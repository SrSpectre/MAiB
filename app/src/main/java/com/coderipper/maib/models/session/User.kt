package com.coderipper.maib.models.session

import android.net.Uri
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
    @ColumnInfo(name = "description") var description: String = "¡Hola, soy nuevo!",
    @ColumnInfo(name = "phone") var phone: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "password") var password: String,
    @ColumnInfo(name = "rate") val rate: Float = 1F,
    @ColumnInfo(name = "avatar") val avatar: Int,
    @ColumnInfo(name = "story") var story: String = "",
)
