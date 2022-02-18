package com.coderipper.maib.usecases.categories.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.CartItemBinding
import com.coderipper.maib.databinding.RecommendationsItemBinding
import com.coderipper.maib.databinding.SizesItemBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.utils.DataBase

class WishlistAdapter(private val products: MutableList<Product>, private val removeWishlistProduct: (productId: Long) -> Unit):
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CartItemBinding.bind(view)

        fun bind(product: Product) {
            binding.run {
                productImage.setImageURI(product.images[0])
                titleText.text = product.name
                priceText.text = product.price
                sizesSpinner.isVisible = false
                colorsSpinner.isVisible = false

                removeButton.setOnClickListener {
                    removeWishlistProduct(product.id)
                }
            }
        }
    }
}