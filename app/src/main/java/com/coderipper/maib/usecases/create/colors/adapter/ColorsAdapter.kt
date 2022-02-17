package com.coderipper.maib.usecases.create.colors.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.ColorsItemBinding

class ColorsAdapter(private val colors: ArrayList<Triple<Int, Int, Int>>):
    RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.colors_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(colors[position])
    }

    override fun getItemCount() = colors.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ColorsItemBinding.bind(view)

        fun bind(color: Triple<Int, Int, Int>) {
            binding.run {
                colorImage.setBackgroundColor(Color.rgb(color.first, color.second, color.third))

                removeButton.setOnClickListener {
                    val index = colors.indexOf(color)
                    colors.remove(color)
                    notifyItemRemoved(index)
                }
            }
        }
    }
}