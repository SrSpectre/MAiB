package com.coderipper.maib.usecases.create.sizes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.SizesItemBinding

class SizesAdapter(private val sizes: ArrayList<Int>):
    RecyclerView.Adapter<SizesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sizes_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sizes[position])
    }

    override fun getItemCount() = sizes.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = SizesItemBinding.bind(view)

        fun bind(size: Int) {
            binding.run {
                sizeText.text = if(size < 10) "0$size" else size.toString()

                removeButton.setOnClickListener {
                    val index = sizes.indexOf(size)
                    sizes.remove(size)
                    notifyItemRemoved(index)
                }
            }
        }
    }
}