package com.coderipper.maib.usecases.main.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.coderipper.maib.R
import com.coderipper.maib.databinding.DashboardSectionsItemBinding
import com.coderipper.maib.models.domain.Product
import com.coderipper.maib.models.session.User

class SectionsPagerAdapter(private val userId: String, private val sections: ArrayList<MutableList<Any>>, private val navController: NavController):
    RecyclerView.Adapter<SectionsPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dashboard_sections_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0)
            holder.bindProducts(sections[position] as MutableList<Product>)
        else holder.bindFollowing(sections[position] as MutableList<User>)
    }

    override fun getItemCount() = sections.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = DashboardSectionsItemBinding.bind(view)

        fun bindProducts(products: MutableList<Product>) {
            binding.elementsList.apply {
                setHasFixedSize(false)
                adapter = UserProductsAdapter(userId, context, products)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }

        fun bindFollowing(following: MutableList<User>) {
            binding.elementsList.apply {
                setHasFixedSize(false)
                adapter = UserFollowingAdapter(following, navController)
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            }
        }
    }
}