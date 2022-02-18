package com.coderipper.maib.usecases.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.BigProductsItemBinding
import com.coderipper.maib.databinding.MyProductsItemBinding
import com.coderipper.maib.databinding.RecommendationsItemBinding
import com.coderipper.maib.databinding.SizesItemBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.utils.DataBase

class ProductsAdapter(private val userId: Long, private val products: List<Product>, private val navController: NavController):
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.big_products_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = BigProductsItemBinding.bind(view)

        fun bind(product: Product) {
            binding.run {
                nameText.text = product.name
                productImage.setImageURI(product.images[0])

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