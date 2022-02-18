package com.coderipper.maib.usecases.main.dashboard.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import com.coderipper.maib.R
import com.coderipper.maib.databinding.MyProductsItemBinding
import com.coderipper.maib.databinding.RecommendationsItemBinding
import com.coderipper.maib.databinding.SizesItemBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.utils.DataBase

class UserProductsAdapter(private val context: Context, private val products: MutableList<Product>, private val navController: NavController):
    RecyclerView.Adapter<UserProductsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.my_products_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = MyProductsItemBinding.bind(view)

        fun bind(product: Product) {
            binding.run {
                productImage.setImageURI(product.images[0])
                nameText.text = product.name
                priceText.text = product.price

                val listPopupWindow = ListPopupWindow(context, null, R.attr.listPopupWindowStyle)

                listPopupWindow.anchorView = recommendationsList

                val stock = if(product.stock) "No stock" else "Stock"

                val items = listOf("Delete", stock)
                val adapter = ArrayAdapter(context, R.layout.mini_sizes_item, items)
                listPopupWindow.setAdapter(adapter)
                listPopupWindow.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
                    val index = products.indexOf(product)
                    if (position == 0) {
                        DataBase.removeProduct(product.userId, product.id)
                        products.remove(product)
                        notifyItemRemoved(index)
                    } else {
                        DataBase.updateStock(product.id, !product.stock)
                        notifyItemChanged(index)
                    }
                    listPopupWindow.dismiss()
                }

                optionsButton.setOnClickListener {
                    listPopupWindow.show()
                }
            }
        }
    }
}