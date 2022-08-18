package com.coderipper.maib.models.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

data class Story(
    val id: String = "",
    val uri: String = "",
)
