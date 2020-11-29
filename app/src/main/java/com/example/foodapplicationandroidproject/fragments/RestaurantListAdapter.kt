package com.example.foodapplicationandroidproject.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodapplicationandroidproject.R

import com.example.foodapplicationandroidproject.database.model.Restaurant

class RestaurantListAdapter:RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {
    private var list = emptyList<Restaurant>()

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.restaurant_image)
        val restaurantNameView: TextView = itemView.findViewById(R.id.restaurant_name)
        val restaurantAddressView: TextView = itemView.findViewById(R.id.restaurant_address)
        val restaurantPriceView: TextView = itemView.findViewById(R.id.restaurant_price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.imageView.setImageResource(R.drawable.logo)
        holder.restaurantNameView.text = currentItem.name
        holder.restaurantAddressView.text = currentItem.address
        holder.restaurantPriceView.text = currentItem.price.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(restaurantList: List<Restaurant>){
        this.list = restaurantList
        notifyDataSetChanged()

    }
}