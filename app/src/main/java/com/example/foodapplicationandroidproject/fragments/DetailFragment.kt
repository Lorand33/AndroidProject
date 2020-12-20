package com.example.foodapplicationandroidproject.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentDetailBinding
import com.example.foodapplicationandroidproject.favourites.FavouriteViewModel
import com.example.foodapplicationandroidproject.favourites.Favourites
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.username
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.favList
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.userFavouriteRestaurantList

class DetailFragment : Fragment() {
    //variable for using Databinding on this fragment
    private lateinit var binding: FragmentDetailBinding
    //variable for the FavouriteViewModel
    private lateinit var favoriteViewModel: FavouriteViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //initializing databinding's variable
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)

        //providing a scope for the viewModel
        favoriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        //variable which will be used as id at inserting a new restaurant to the favourite_table
        var index = 0
        /**
         * giving TextView element's a new value, received from MainScreenFragment using Bundle
         * the values sent by using Bundle are stored in the variable "arguments", every key has a unique key
         */
        binding.detailRestaurantName.text = arguments?.getString("NAME")
        binding.detailRestaurantAddress.text = arguments?.getString("ADDRESS")
        binding.detailRestaurantCity.text = arguments?.getString("CITY")
        binding.detailRestaurantState.text = arguments?.getString("STATE")
        binding.detailRestaurantArea.text = arguments?.getString("AREA")
        binding.detailRestaurantCountry.text = arguments?.getString("COUNTRY")
        binding.detailRestaurantPostal.text = arguments?.getString("POSTAL")
        binding.detailRestaurantPhone.text = arguments?.getString("PHONE")

        /**
         * set what to happen when the "Call number" button is pressed
         */
        binding.callRestaurant.setOnClickListener{
            //get the String
            var phoneNumber = binding.detailRestaurantPhone.text.toString()
            //cleaning the number
            phoneNumber = phoneNumber.replace("[^0-9]", "")
            //build the phone number
            val build = "tel:" + phoneNumber.trim().substring(0,10)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(build)
            //starting activity -> the call function of the phone appears, with the number typed in
            startActivity(intent)
        }

        /**
         * get the current restaurant's index on the table
         */
        for(restaurants in userFavouriteRestaurantList){
            if(restaurants.restaurantName == binding.detailRestaurantName.text.toString()){
                index = restaurants.id
            }
        }

        /**
         * setting favourite button's state
         */

        //if the restaurant's name is included on the favourite restaurants
        if(favList.contains(binding.detailRestaurantName.text.toString())){
            //the favourite button is turned on
            binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
        }
        //in other case
        else{
            //the favourite button is turned off
            binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
        }

        /**
         * set what to happen the favourite button is clicked
         */
        binding.detailFavouriteButton.setOnClickListener{
            //if the favourite button is turned on
            if(binding.detailFavouriteButton.drawable.constantState == requireContext().resources.getDrawable(android.R.drawable.btn_star_big_on).constantState){
                //it will be turned off
                binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
                //if the list contains the current restaurant
                if(favList.contains(binding.detailRestaurantName.text.toString())){
                    //the restaurant should be removed
                    //the parameters for the function are: restaurants name from the TextView converted to String, username got from the login page(companion object)
                    favoriteViewModel.deleteFavorite(binding.detailRestaurantName.text.toString(),username)
                    //message to check what happens
                    Toast.makeText(context, "Restaurant removed from favorites", Toast.LENGTH_SHORT).show()
                    //remove restaurant from the helper list
                    favList.remove(binding.detailRestaurantName.text.toString())
                }
                //user can reverse the transaction by pressing back button
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.addToBackStack(null)
                transaction.commit()
            }
            //in other case
            else if (binding.detailFavouriteButton.drawable.constantState == requireContext().resources.getDrawable(android.R.drawable.btn_star_big_off).constantState){
                //it will be turned on
                binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
                //if the list does not contain the current restaurant
                if(!favList.contains(binding.detailRestaurantName.text.toString())){
                    //the restaurant should be added to the table
                    //we will create a new Favourites type object, parameters: index calculated at the beginning, restaurants name from the TextView converted to String, username got from the login page(companion object)
                    favoriteViewModel.addToFavorites(Favourites(index, binding.detailRestaurantName.text.toString(),username))
                    //message to check what happens
                    Toast.makeText(context, "Restaurant added to favorites", Toast.LENGTH_SHORT).show()
                    //add restaurant to the helper list
                    favList.add(binding.detailRestaurantName.text.toString())
                }
                //user can reverse the transaction by pressing back button
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return binding.root
    }

}