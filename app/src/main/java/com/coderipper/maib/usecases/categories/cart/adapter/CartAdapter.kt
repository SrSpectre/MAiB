package com.coderipper.maib.usecases.categories.cart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.CartItemBinding
import com.coderipper.maib.databinding.RecommendationsItemBinding
import com.coderipper.maib.databinding.SizesItemBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.utils.DataBase

class CartAdapter(private val context: Context, private val products: MutableList<Product>, private val removeCartProduct: (productId: Long) -> Unit):
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
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

                if (product.sizes != null) {
                    val items = arrayListOf<String>()
                    product.sizes?.forEach {
                        items.add(
                            if(it.size < 10) "0${it.size}"
                            else "${it.size}"
                        )
                    }
                    val adapter = ArrayAdapter(context, R.layout.mini_sizes_item, items)
                    (sizesSpinner.editText as? AutoCompleteTextView)?.apply {
                        setAdapter(adapter)
                        setText(items[0])
                    }
                } else {
                    sizesSpinner.editText?.isEnabled = false
                    sizesSpinner.editText?.setText("INVALIDO")
                }

                if (product.colors != null) {
                    val items = arrayListOf<String>()
                    product.colors?.forEach {
                        items.add(
                            if(it.color < 10) "0${it.color}"
                            else "${it.color}"
                        )
                    }
                    val adapter = ArrayAdapter(context, R.layout.mini_colors_item, items)
                    (colorsSpinner.editText as? AutoCompleteTextView)?.apply {
                        setAdapter(adapter)
                        setText(items[0])
                    }
                } else {
                    colorsSpinner.editText?.isEnabled = false
                    colorsSpinner.editText?.setText("INVALIDO")
                }

                removeButton.setOnClickListener {
                    removeCartProduct(product.id)
                }
            }
        }
    }
}