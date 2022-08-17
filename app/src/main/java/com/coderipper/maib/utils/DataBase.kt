package com.coderipper.maib.utils

import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.coderipper.maib.R
import com.coderipper.maib.models.domain.*
import com.coderipper.maib.models.session.User

object DataBase {
    // Fake viewmodel
    val sizes = MutableLiveData<ArrayList<Int>>()
    val colors = MutableLiveData<ArrayList<Triple<Int, Int, Int>>>()

    // Fake database
    private val products = arrayListOf<Product>()
    private val following = arrayListOf<Following>()
    private val carts = arrayListOf<Cart>()
    private val wishlists = arrayListOf<Wishlist>()
    private val users = arrayListOf(
        User(
            id = 1L,
            name = "Lissa",
            last = "Roberts",
            description = "Hola, soy una pintora",
            phone = "+52XXXXXXXX2574",
            email = "lissa.roberts41@maib.com",
            password = "hola1",
            rate = 3.6F,
            avatar = R.drawable.avatar4
        ),
        User(
            id = 2L,
            name = "Joseph",
            last = "Alphs",
            description = "Hola, soy un diseñador de ropa para publico femenino.",
            phone = "+52XXXXXXXX6944",
            email = "joseph@maib.com",
            password = "hola1",
            rate = 4.6F,
            avatar = R.drawable.avatar3
        ),
        User(
            id = 3L,
            name = "Alex",
            last = "Lina",
            description = "Escultora y diseñadora de interiores",
            phone = "+52XXXXXXXX4987",
            email = "designs.lover@maib.com",
            password = "hola1",
            rate = 4.6F,
            avatar = R.drawable.avatar2
        ),
        User(
            id = 4L,
            name = "Kotori",
            last = "Xianji",
            description = "Soy una escritora originaria de Japón.",
            phone = "+52XXXXXXXX8746",
            email = "japan.art@maib.com",
            password = "hola1",
            rate = 4.6F,
            avatar = R.drawable.avatar4
        ),
        User(
            id = 5L,
            name = "Jose",
            last = "Cuevas",
            description = "Soy un artista digital nuevo",
            phone = "+52XXXXXXXX5823",
            email = "cuevas@maib.com",
            password = "hola1",
            rate = 4.6F,
            avatar = R.drawable.avatar6
        ),
        User(
            id = 6L,
            name = "Anny",
            last = "Maldy",
            description = "Diseñadora de ropa profesional",
            phone = "+52XXXXXXXX2121",
            email = "anny.design@maib.com",
            password = "hola1",
            rate = 5.0F,
            avatar = R.drawable.avatar5
        ),
        User(
            id = 7L,
            name = "Jaison",
            last = "Supra",
            description = "Me gusta hacer objetos de anime",
            phone = "+52XXXXXXXX6895",
            email = "jaison@maib.com",
            password = "hola1",
            rate = 5.0F,
            avatar = R.drawable.avatar6
        )
    )

    init {
        products.addAll(
            arrayListOf(
                Product(
                    userId = users[6].id,
                    name = "Anime llaveros",
                    price = "1026.00",
                    description = "Comparto mi gusto por el anime.",
                    category = Categories.NO_SPECIFIED.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.others_1)
                    )
                ),
                Product(
                    userId = users[0].id,
                    name = "Vistas Montaña",
                    price = "1026.00",
                    description = "Me inspiré en las montañas de Japón.",
                    category = Categories.PAINTING.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.montains_1)
                    )
                ),
                Product(
                    userId = users[2].id,
                    name = "Silla cool",
                    price = "695.00",
                    description = "Silla para descansar después de un largo día.",
                    category = Categories.INDOOR.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.chair_1)
                    )
                ),
                Product(
                    userId = users[2].id,
                    name = "Silla piedra",
                    price = "845.00",
                    description = "Inspirada en los gamers",
                    category = Categories.INDOOR.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.chair_2)
                    )
                ),
                Product(
                    userId = users[2].id,
                    name = "Silla lampara",
                    price = "356.00",
                    description = "Hecha para los lectores.",
                    category = Categories.INDOOR.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.chair_3)
                    )
                ),
                Product(
                    userId = users[3].id,
                    name = "Viaje en el tiempo",
                    price = "584.00",
                    description = "Viajar es lo mejor y más en el tiempo.",
                    category = Categories.BOOKS.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.light_1)
                    )
                ),
                Product(
                    userId = users[3].id,
                    name = "Amor en hostal",
                    price = "458.00",
                    description = "Para lectores romanticos.",
                    category = Categories.BOOKS.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.light_2)
                    )
                ),
                Product(
                    userId = users[2].id,
                    name = "Reflejo",
                    price = "289.00",
                    description = "Amor a la estructura.",
                    category = Categories.SCULPTURE.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.scupture_1)
                    )
                ),
                Product(
                    userId = users[4].id,
                    name = "Viaje por México",
                    price = "584.00",
                    description = "Mejor que conocer lugares nuevos.",
                    category = Categories.DIGITAL.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.digital_1)
                    )
                ),
                Product(
                    userId = users[4].id,
                    name = "inspiración",
                    price = "325.00",
                    description = "Te hace volar.",
                    category = Categories.DIGITAL.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.digital_2)
                    )
                ),
                Product(
                    userId = users[1].id,
                    name = "Vestido Vintage",
                    price = "226.00",
                    description = "Vestido sencillo para dama.",
                    category = Categories.CLOSET.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.vintage_1)
                    ),
                    sizes = arrayListOf(
                        Size(
                            size = 2
                        ),
                        Size(
                            size = 4
                        ),
                        Size(
                            size = 6
                        ),
                        Size(
                            size = 8
                        )
                    ),
                    colors = arrayListOf(
                        ColorEntity(
                            color = Color.RED
                        ),
                        ColorEntity(
                            color = Color.BLUE
                        ),
                        ColorEntity(
                            color = Color.BLACK
                        )
                    )
                ),
                Product(
                    userId = users[1].id,
                    name = "Botas rock",
                    price = "238.00",
                    description = "Botas para gente bien rockera.",
                    category = Categories.CLOSET.ordinal,
                    images = arrayListOf(
                        Uri.parse("android.resource://com.coderipper.maib/" + R.drawable.rock_1)
                    ),
                    sizes = arrayListOf(
                        Size(
                            size = 22
                        ),
                        Size(
                            size = 23
                        ),
                        Size(
                            size = 24
                        ),
                        Size(
                            size = 25
                        )
                    ),
                    colors = arrayListOf(
                        ColorEntity(
                            color = Color.WHITE
                        ),
                        ColorEntity(
                            color = Color.BLUE
                        ),
                        ColorEntity(
                            color = Color.BLACK
                        )
                    )
                )
            )
        )

        following.addAll(
            arrayListOf(
                Following(
                    userId = users[0].id,
                    followerId = users[1].id
                ),
                Following(
                    userId = users[1].id,
                    followerId = users[0].id
                ),
                Following(
                    userId = users[5].id,
                    followerId = users[0].id
                ),
                Following(
                    userId = users[5].id,
                    followerId = users[1].id
                ),
                Following(
                    userId = users[5].id,
                    followerId = users[2].id
                ),
                Following(
                    userId = users[5].id,
                    followerId = users[3].id
                ),
                Following(
                    userId = users[1].id,
                    followerId = users[5].id
                ),
                Following(
                    userId = users[3].id,
                    followerId = users[1].id
                ),
                Following(
                    userId = users[3].id,
                    followerId = users[4].id
                ),
                Following(
                    userId = users[3].id,
                    followerId = users[0].id
                ),
                Following(
                    userId = users[3].id,
                    followerId = users[5].id
                )
            )
        )
    }

    fun loginUser(email: String, password: String): User? {
        return users.find { it.email == email && it.password == password }
    }

    fun getUserById(id: Long): User? {
        return users.find { it.id == id }
    }

    fun getProductByUserId(id: Long): Product? {
        return products.firstOrNull { it.userId == id }
    }

    fun getMoreArtists(activeId: Long, userId: Long): List<User> {
        val artists = users.filter { it.id != activeId && it.id != userId }
        return if(artists.size > 5) artists.subList(0, 5) else artists
    }

    fun getProductsByUserId(userId: Long): MutableList<Product> {
        return products.filter { it.userId == userId }.toMutableList()
    }

    fun getBuyProducts(userId: Long, categories: Int): MutableList<Product> {
        return products.filter {
            it.userId != userId && it.category == categories && it.stock
        }.toMutableList()
    }

    fun getPromotional(userId: Long): Product {
        return products.first { it.userId != userId && it.stock }
    }

    fun getRecommendations(userId: Long, promotionalId: Long): List<Product> {
        return products.filter {
            it.userId != userId && it.id != promotionalId && it.stock
        }
    }

    fun getFollowing(userId: Long): MutableList<User> {
        val followers = arrayListOf<User>()
        following.filter { it.followerId == userId }.forEach { follower ->
            users.find { it.id == follower.userId }?.let {
                followers.add(it)
            }
        }
        return followers.toMutableList()
    }

    fun getFollowersCount(userId: Long): Int {
        return following.filter { it.userId == userId }.size
    }

    fun getStoryFromUser(userId: Long): String? {
        return users.find { it.id == userId }?.story
    }

    fun getCartByUserId(userId: Long): MutableList<Product> {
        val cartList = arrayListOf<Product>()
        carts.filter { it.userId == userId }.forEach { cart ->
            products.find { it.id == cart.productId }?.let {
                cartList.add(it)
            }
        }
        return cartList.toMutableList()
    }

    fun getWishlistByUserId(userId: Long): MutableList<Product> {
        val wishlist = arrayListOf<Product>()
        wishlists.filter { it.userId == userId }.forEach { wishItem ->
            products.find { it.id == wishItem.productId }?.let {
                wishlist.add(it)
            }
        }
        return wishlist.toMutableList()
    }

    fun getProductsBySearch(text: String): List<Product> {
        return products.filter { it.name.contains(text) }
    }

    fun getIsFollowing(userId: Long, followerId: Long): Boolean {
        val found = following.filter {
            it.userId == userId && it.followerId == followerId
        }
        return found.isNotEmpty()
    }

    // Set
    fun setUser(user: User) {
        users.add(user)
    }

    fun setToCart(userId: Long, productId: Long) {
        carts.add(
            Cart(
                userId = userId,
                productId = productId
            )
        )
    }

    fun setToWishlist(userId: Long, productId: Long) {
        wishlists.add(
            Wishlist(
                userId = userId,
                productId = productId
            )
        )
    }

    fun setFollowing(userId: Long, followerId: Long) {
        following.add(
            Following (
                userId = userId,
                followerId = followerId
            )
        )
    }

    fun setProduct(userId: Long, name: String, price: String,
                   description: String, category: Int, images: ArrayList<Uri>,
                   sizes: ArrayList<Size>? = null, colors: ArrayList<ColorEntity>? = null
    ) {
        val product = Product(
            userId = userId,
            name = name,
            price = price,
            description = description,
            category = category,
            images = images
        )
        sizes?.let {
            product.sizes = it
        }

        colors?.let {
            product.colors = it
        }

        products.add(product)
    }

    // Update
    fun updateDescription(userId: Long, description: String) {
        users.find { it.id == userId }?.description = description
    }

    fun updatePhone(userId: Long, phone: String) {
        users.find { it.id == userId }?.phone = phone
    }

    fun updateEmail(userId: Long, email: String) {
        users.find { it.id == userId }?.email = email
    }

    fun updatePassword(userId: Long, password: String) {
        users.find { it.id == userId }?.password = password
    }

    fun updateStory(userId: Long, story: String) {
        users.find { it.id == userId }?.story = story
    }

    fun updateStock(id: Long, stock: Boolean) {
        products.find { it.id == id }?.stock = stock
    }

    // Remove
    fun removeFromCart(userId: Long, productId: Long) {
        carts.removeIf { it.userId == userId && it.productId == productId }
    }

    fun removeFromWishlist(userId: Long, productId: Long) {
        wishlists.removeIf { it.userId == userId && it.productId == productId }
    }

    fun removeProduct(userId: Long, productId: Long) {
        products.removeIf { it.userId == userId && it.id == productId }
    }

    fun removeFollowing(userId: Long, followerId: Long) {
        following.removeIf { it.userId == userId && it.followerId == followerId }
    }
}