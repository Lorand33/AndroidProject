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

/**
 * adapter class for the recycler view used at the profile page
 * parameters: list with the favourite restaurants
 */
class FavouriteListAdapter(private val list: List<Favourites>): RecyclerView.Adapter<FavouriteListAdapter.MyViewHolder>() {

    //class defined for getting item's elements
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.favouriteRestaurantImage)
        val restaurantNameView: TextView = itemView.findViewById(R.id.favouriteRestaurantName)
    }

    //creating the view holder -> it's role is to describe an item's view and metadata about its place within the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.favorite_layout,parent,false)
        return MyViewHolder(itemView)
    }

    //build the view and data of an item
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //getting the item
        val currentItem = list[position]
        //set the image -> in my case every picture will be the same
        holder.imageView.setImageResource(R.drawable.logo)
        //set restaurant's name
        holder.restaurantNameView.text = currentItem.restaurantName
    }

    //function which returns our list's size
    override fun getItemCount(): Int {
        return list.size
    }
}