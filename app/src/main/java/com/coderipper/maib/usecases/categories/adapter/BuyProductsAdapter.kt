package com.coderipper.maib.usecases.categories.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.*
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.session.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.squareup.picasso.Picasso

class BuyProductsAdapter(private val userId: String, private val products: MutableList<Product>, private val navController: NavController, val openProfile: (userId: String) -> Unit):
    RecyclerView.Adapter<BuyProductsAdapter.ViewHolder>() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ProductItemBinding.bind(view)

        fun bind(product: Product) {
            binding.run {
                db.collection("users").get().addOnSuccessListener { data ->
                    if (!data.isEmpty) {
                        val docs = data.filter { it.id != userId }

                        docs.forEach { doc ->
                            val user = doc.toObject<User>()
                            user.products.forEach { p ->
                                if (p.id == product.id) {
                                    avatarImage.setImageResource(user.avatar)
                                    avatarImage.setOnClickListener {
                                        openProfile(doc.id)
                                    }
                                }
                            }
                        }
                    }
                }

                Picasso.with(binding.root.context).load(product.images[0].uri.toUri()).into(productImage)
                nameText.text = product.name
                priceText.text = product.price

                addCartButton.addOnCheckedChangeListener { _, isChecked ->
                    /*if(isChecked)
                        DataBase.setToCart(userId, product.id)
                    else DataBase.removeFromCart(userId, product.id)*/
                }

                addWishlistButton.addOnCheckedChangeListener { _, isChecked ->
                    /*if(isChecked)
                        DataBase.setToWishlist(userId, product.id)
                    else DataBase.removeFromWishlist(userId, product.id)*/
                }
            }
        }
    }
}