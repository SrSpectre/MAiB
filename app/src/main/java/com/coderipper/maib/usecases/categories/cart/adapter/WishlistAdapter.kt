package com.coderipper.maib.usecases.categories.cart.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.CartItemBinding
import com.coderipper.maib.models.domain.Product
import com.squareup.picasso.Picasso

class WishlistAdapter(private val products: MutableList<Product>, private val removeWishlistProduct: (productId: String) -> Unit):
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
                Picasso.with(binding.root.context).load(product.images[0].uri.toUri()).into(productImage)
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