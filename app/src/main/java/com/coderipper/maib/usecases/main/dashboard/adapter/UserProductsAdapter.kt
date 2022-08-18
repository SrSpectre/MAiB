package com.coderipper.maib.usecases.main.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.MyProductsItemBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.session.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class UserProductsAdapter(
    private val userId: String,
    private val context: Context,
    private val products: MutableList<Product>
):
    RecyclerView.Adapter<UserProductsAdapter.ViewHolder>() {
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

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
                Picasso.with(binding.root.context).load(product.images[0].uri.toUri()).into(productImage)
                nameText.text = product.name
                priceText.text = product.price

                val listPopupWindow = ListPopupWindow(context, null, R.attr.listPopupWindowStyle)

                listPopupWindow.anchorView = recommendationsList

                val items = listOf("Delete")
                val adapter = ArrayAdapter(context, R.layout.mini_sizes_item, items)
                listPopupWindow.setAdapter(adapter)
                listPopupWindow.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                    val index = products.indexOf(product)
                    if (position == 0) {
                        db.collection("users").document(userId).get().addOnSuccessListener { data ->
                            if(data.exists()) {
                                val user = data.toObject<User>()
                                if (user != null) {
                                    val p = user.products.first { it.id == product.id }
                                    user.products.remove(p)
                                    val storageRef = storage.reference
                                    p.images.forEach { image ->
                                        storageRef.child(image.id).delete()
                                        db.collection("users").document(userId).update("products", user.products)
                                    }
                                }
                            }
                        }
                        products.remove(product)
                        notifyItemRemoved(index)
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