package com.example.foodapplicationandroidproject.fragments

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
import com.example.foodapplicationandroidproject.favorites.FavoriteViewModel
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.database.model.Restaurant
import com.example.foodapplicationandroidproject.databinding.FragmentDetailBinding
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.counter
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.favList
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.isFavorite

class DetailFragment : Fragment() {
    companion object{
        var getRestaurant : MutableList<Restaurant> = mutableListOf()
    }
    private lateinit var binding: FragmentDetailBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.detailRestaurantName.text = arguments?.getString("NAME")
        binding.detailRestaurantAddress.text = arguments?.getString("ADDRESS")
        binding.detailRestaurantCity.text = arguments?.getString("CITY")
        binding.detailRestaurantState.text = arguments?.getString("STATE")
        binding.detailRestaurantArea.text = arguments?.getString("AREA")
        binding.detailRestaurantCountry.text = arguments?.getString("COUNTRY")
        binding.detailRestaurantPostal.text = arguments?.getString("POSTAL")
        binding.detailRestaurantPhone.text = arguments?.getString("PHONE")

        binding.callRestaurant.setOnClickListener{
            var phoneNumber = binding.detailRestaurantPhone.text.toString()
            phoneNumber = phoneNumber.replace("[^0-9]", "")
            val build = "tel:" + phoneNumber.trim().substring(0,10)
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse(build)
            startActivity(intent)
        }

        if(favList.contains(getRestaurant[0].name)){
            binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
        }
        else{
            binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
        }

        binding.detailFavouriteButton.setOnClickListener{
            if(counter == 1){
                binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
                counter = 0
                isFavorite = false
                if(favList.contains(getRestaurant[0].name)){
                    favList.remove(getRestaurant[0].name)
                    Toast.makeText(context, "Restaurant removed from favorites", Toast.LENGTH_SHORT).show()
                }
            }
            else if (counter == 0){
                binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
                counter = 1
                isFavorite = true
                if(!favList.contains(getRestaurant[0].name)){
                    favList.add(getRestaurant[0].name)
                    Toast.makeText(context, "Restaurant added from favorites", Toast.LENGTH_SHORT).show()
                }
            }
        }

        /*favoriteViewModel.getFavoriteRestaurant().observe(viewLifecycleOwner, Observer<Favorites> {
            if(!isFavorite &&  counter == 0 && favList.contains(it.restaurantName)){
                favoriteViewModel.deleteFromFavorites(it)
            }
            else if(isFavorite && counter == 1 && !favList.contains(it.restaurantName)){
                val restaurant = Favorites(0,it.restaurantName,name)
                favoriteViewModel.addToFavorites(restaurant)
            }
        })*/

        return binding.root
    }

}