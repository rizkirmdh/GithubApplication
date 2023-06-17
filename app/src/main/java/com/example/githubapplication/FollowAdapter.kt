package com.example.githubapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class FollowAdapter (private val listAccount: List<String>, private val listPhoto: List<String>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_account, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.tvItem.text = listAccount[position]
        Glide.with(viewHolder.itemView.context)
            .load(listPhoto[position])
            .into(viewHolder.ivPhoto)
    }
    override fun getItemCount() = listAccount.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvName)
        val ivPhoto: ImageView = view.findViewById(R.id.ivPhoto)
    }
}