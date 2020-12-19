package com.example.foodapplicationandroidproject.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.favourites.Favourites

class FavouriteListAdapter(private val list: List<Favourites>, private val context: Context): RecyclerView.Adapter<FavouriteListAdapter.MyViewHolder>() {

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.favouriteRestaurantImage)
        val restaurantNameView: TextView = itemView.findViewById(R.id.favouriteRestaurantName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteListAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favorite_layout,parent,false)
        return FavouriteListAdapter.MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavouriteListAdapter.MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.imageView.setImageResource(R.drawable.logo)
        holder.restaurantNameView.text = currentItem.restaurantName

    }

    override fun getItemCount(): Int {
        return list.size
    }

}