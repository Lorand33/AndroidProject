package com.example.foodapplicationandroidproject.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapplicationandroidproject.R

import com.example.foodapplicationandroidproject.database.model.Restaurant

class RestaurantListAdapter(private val List: List<Restaurant>,private val context: Context):RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = List[position]
        //holder.imageView.setImageResource(R.drawable.logo)
        Glide.with(context).load(currentItem.image_url).into(holder.imageView)
        holder.restaurantNameView.text = currentItem.name
        holder.restaurantAddressView.text = currentItem.address
        holder.restaurantPriceView.text = "Price: ${currentItem.price}"
        holder.itemView.setOnClickListener{
                val bundle = Bundle()
                //bundle.putString("ID",currentItem.id.toString())
                bundle.putString("NAME", currentItem.name)
                bundle.putString("ADDRESS", currentItem.address)
                bundle.putString("CITY", currentItem.city)
                bundle.putString("STATE", currentItem.state)
                bundle.putString("AREA", currentItem.area)
                bundle.putString("POSTAL", currentItem.postal_code)
                bundle.putString("COUNTRY", currentItem.country)
                bundle.putString("PHONE", currentItem.phone)

                println("There was a click**************")
                holder.itemView.findNavController().navigate(R.id.action_mainScreenFragment_to_detailFragment,bundle)

        }
    }

    override fun getItemCount(): Int {
        return List.size
    }

}