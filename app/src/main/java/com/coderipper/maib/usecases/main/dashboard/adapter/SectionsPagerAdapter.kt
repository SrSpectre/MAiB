package com.coderipper.maib.usecases.main.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coderipper.maib.R
import com.coderipper.maib.databinding.DashboardSectionsItemBinding

class SectionsPagerAdapter(private val sections: ArrayList<String>):
    RecyclerView.Adapter<SectionsPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dashboard_sections_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    override fun getItemCount() = sections.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = DashboardSectionsItemBinding.bind(view)

        fun bind(element: String) {

        }
    }
}