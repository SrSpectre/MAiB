package com.coderipper.maib.usecases.signin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.AvatarItemBinding

class AvatarAdapter(private val avatarsIds: Array<Int>, val selectedAvatar: (imgId: Int) -> Unit): RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = AvatarItemBinding.bind(view)

        fun bind(imgId: Int) {
            binding.avatarImg.setImageResource(imgId)
            binding.avatarImg.setOnClickListener {
                selectedAvatar(imgId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.avatar_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(avatarsIds[position])
    }

    override fun getItemCount() = avatarsIds.size
}