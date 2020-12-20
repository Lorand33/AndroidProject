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
import com.example.foodapplicationandroidproject.restaurants.model.Restaurant
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.favList

/**
 * adapter class for the recycler view used at the main screen
 * parameters: list with the restaurants, context
 */
class RestaurantListAdapter(private val restaurantList: List<Restaurant>,private val context: Context):RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {

    //class defined for getting item's elements
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        //assign every variable an element from the item_layout
        val imageView: ImageView = itemView.findViewById(R.id.restaurant_image)
        val restaurantNameView: TextView = itemView.findViewById(R.id.restaurant_name)
        val restaurantAddressView: TextView = itemView.findViewById(R.id.restaurant_address)
        val restaurantPriceView: TextView = itemView.findViewById(R.id.restaurant_price)
        val favoriteIcon : ImageView = itemView.findViewById(R.id.favourite_button)
    }

    //creating the view holder -> it's role is to describe an item's view and metadata about its place within the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return MyViewHolder(itemView)
    }

    //build the view and data of an item
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //getting the item
        val currentItem = restaurantList[position]
        //load the restaurant's image using Glide
        Glide.with(context).load(currentItem.image_url).into(holder.imageView)
        ////set restaurant's name
        holder.restaurantNameView.text = currentItem.name
        //set restaurant's address
        holder.restaurantAddressView.text = currentItem.address
        //set restaurant's price
        holder.restaurantPriceView.text = "Price: ${currentItem.price}"

        /**
         * setting favourite button's state
         */

        //if the current restaurants name is included on the favourite restaurants
        if(favList.contains(currentItem.name)){
            //the favourite button is turned on
            holder.favoriteIcon.setImageResource(android.R.drawable.btn_star_big_on)
        }
        //in other case
        else{
            //the favourite button is turned off
            holder.favoriteIcon.setImageResource(android.R.drawable.btn_star_big_off)
        }

        /**
         * set what happens when user clicks on one of recycler view's element
         */
        holder.itemView.setOnClickListener{
            //variable defined for bundle
            val bundle = Bundle()
            //information about the restaurant will be sent using to the detail page using bundle
            //first parameter: KEY, second parameter: one of the current element's data
            bundle.putString("NAME", currentItem.name)
            bundle.putString("ADDRESS", currentItem.address)
            bundle.putString("CITY", currentItem.city)
            bundle.putString("STATE", currentItem.state)
            bundle.putString("AREA", currentItem.area)
            bundle.putString("POSTAL", currentItem.postal_code)
            bundle.putString("COUNTRY", currentItem.country)
            bundle.putString("PHONE", currentItem.phone)

            //we will be navigated to the detail page
            holder.itemView.findNavController().navigate(R.id.action_mainScreenFragment_to_detailFragment,bundle)
        }
    }

    //function which returns our list's size
    override fun getItemCount(): Int {
        return restaurantList.size
    }

}