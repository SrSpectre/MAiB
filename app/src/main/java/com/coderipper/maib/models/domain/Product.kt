package com.coderipper.maib.models.domain

import java.util.*
import kotlin.collections.ArrayList

data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val category: Int = -1,
    var stock: Boolean = true,
    val images: ArrayList<Image> = arrayListOf(),
    var sizes: ArrayList<Size>? = null,
    var colors: ArrayList<ColorEntity>? = null
)
