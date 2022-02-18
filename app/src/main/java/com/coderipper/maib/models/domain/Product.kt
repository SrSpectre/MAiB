package com.coderipper.maib.models.domain

import android.net.Uri
import androidx.room.*

@Entity(
    tableName = "products",
    indices = [Index(value = ["phone", "email"], unique = true)]
)
data class Product(
    @PrimaryKey val id: Long = System.nanoTime(),
    @ColumnInfo(name = "id_user") val userId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "category") val category: Int,
    @ColumnInfo(name = "stack") var stock: Boolean = true,
    @Ignore val images: ArrayList<Uri>,
    @Ignore var sizes: ArrayList<Size>? = null,
    @Ignore var colors: ArrayList<ColorEntity>? = null
)
