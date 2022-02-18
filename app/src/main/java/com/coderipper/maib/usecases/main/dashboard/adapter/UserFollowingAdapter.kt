package com.coderipper.maib.usecases.main.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.FollowingItemBinding
import com.coderipper.maib.databinding.MyProductsItemBinding
import com.coderipper.maib.models.session.User
import com.coderipper.maib.usecases.main.MainFragmentDirections
import com.coderipper.maib.utils.DataBase

class UserFollowingAdapter(private val following: List<User>, private val navController: NavController):
    RecyclerView.Adapter<UserFollowingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.following_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(following[position])
    }

    override fun getItemCount() = following.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = FollowingItemBinding.bind(view)

        fun bind(user: User) {
            binding.run {
                val product = DataBase.getProductByUserId(user.id)
                product?.let {
                    userImage.setImageURI(it.images[0])
                }
                avatarImage.setImageResource(user.avatar)
                userNameText.text = user.name
                userDescriptionText.text = user.description
                followingText.text = DataBase.getFollowersCount(user.id).toString()
                rateText.text = user.rate.toString()

                followingCard.setOnClickListener {
                    navController.navigate(MainFragmentDirections.toProfile(user.id))
                }
            }
        }
    }
}