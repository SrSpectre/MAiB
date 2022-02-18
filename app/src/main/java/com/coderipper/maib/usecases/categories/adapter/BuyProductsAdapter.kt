package com.coderipper.maib.usecases.categories.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.*
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.utils.DataBase
import com.coderipper.maib.utils.getLongValue

class BuyProductsAdapter(private val userId: Long, private val products: MutableList<Product>, private val navController: NavController, val openProfile: (userId: Long) -> Unit):
    RecyclerView.Adapter<BuyProductsAdapter.ViewHolder>() {
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
                DataBase.getUserById(product.userId)?.avatar?.let {
                    avatarImage.setImageResource(it)
                }

                avatarImage.setOnClickListener {
                    openProfile(product.userId)
                }

                productImage.setImageURI(product.images[0])
                nameText.text = product.name
                priceText.text = product.price

                addCartButton.addOnCheckedChangeListener { button, isChecked ->
                    if(isChecked)
                        DataBase.setToCart(userId, product.id)
                    else DataBase.removeFromCart(userId, product.id)
                }

                addWishlistButton.addOnCheckedChangeListener { button, isChecked ->
                    if(isChecked)
                        DataBase.setToWishlist(userId, product.id)
                    else DataBase.removeFromWishlist(userId, product.id)
                }
            }
        }
    }
}