package com.coderipper.maib.models.session

import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.domain.Story

data class User(
    val name: String = "",
    val last: String = "",
    var description: String = "¡Hola, soy nuevo!",
    var phone: String = "",
    var email: String = "",
    var password: String = "",
    val rate: Float = 1F,
    val avatar: Int = -1,
    var story: Story? = null,
    val cart: ArrayList<String> = ArrayList(),
    val wishlist: ArrayList<String> = ArrayList(),
    val following: ArrayList<String> = ArrayList(),
    val followers: ArrayList<String> = ArrayList(),
    val products: ArrayList<Product> = ArrayList()
)
