package com.coderipper.maib.usecases.profile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.*
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.utils.DataBase

class MoreArtistsAdapter(private val users: List<User>, private val navController: NavController):
    RecyclerView.Adapter<MoreArtistsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.mini_artist_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = MiniArtistItemBinding.bind(view)

        fun bind(user: User) {
            binding.run {
                val product = DataBase.getProductByUserId(user.id)
                product?.let {
                    userImage.setImageURI(it.images[0])
                }
                avatarImage.setImageResource(user.avatar)
                userNameText.text = user.name
                followingText.text = DataBase.getFollowersCount(user.id).toString()

                artistCard.setOnClickListener {
                    navController.navigate(MainFragmentDirections.toProfile(user.id))
                }
            }
        }
    }
}