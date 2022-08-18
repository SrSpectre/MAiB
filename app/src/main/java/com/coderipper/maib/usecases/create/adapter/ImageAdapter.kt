package com.coderipper.maib.usecases.create.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.ImagesItemBinding

class ImageAdapter(private val fragment: Fragment, private val images: MutableList<Uri?>, private val removeImage: (uri: Uri) -> Unit):
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.images_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ImagesItemBinding.bind(view)

        fun bind(uri: Uri?) {
            binding.run {
                if (uri != null) {
                    imageView.setImageURI(uri)
                    removeButton.setOnClickListener {
                        removeImage(uri)
                    }
                } else removeButton.isVisible = false

                imageCard.setOnClickListener {
                    Intent(Intent.ACTION_GET_CONTENT).also {
                        it.type = "image/*"
                        fragment.startActivityForResult(it, 0)
                    }
                }
            }
        }
    }
}