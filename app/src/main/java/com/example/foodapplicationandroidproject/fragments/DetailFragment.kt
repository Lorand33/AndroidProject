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
import androidx.navigation.fragment.findNavController
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentDetailBinding
import com.example.foodapplicationandroidproject.favourites.FavouriteViewModel
import com.example.foodapplicationandroidproject.favourites.Favourites
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.username
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.favList
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.favouriteRestaurantList
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.userFavouriteRestaurantList
import kotlinx.android.synthetic.main.fragment_main_screen.*

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var favoriteViewModel: FavouriteViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)

        favoriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        var index = 0
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

        for(restaurants in userFavouriteRestaurantList){
            if(restaurants.restaurantName == binding.detailRestaurantName.text.toString()){
                index = restaurants.id
            }
        }

        if(favList.contains(binding.detailRestaurantName.text.toString())){
            binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
        }
        else{
            binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
        }

        binding.detailFavouriteButton.setOnClickListener{
            if(binding.detailFavouriteButton.drawable.constantState == requireContext().resources.getDrawable(android.R.drawable.btn_star_big_on).constantState){
                binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_off)
                if(favList.contains(binding.detailRestaurantName.text.toString())){
                    favoriteViewModel.deleteFavorite(binding.detailRestaurantName.text.toString(),username)
                    Toast.makeText(context, "Restaurant removed from favorites", Toast.LENGTH_SHORT).show()
                    favList.remove(binding.detailRestaurantName.text.toString())
                }
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.addToBackStack(null)
                transaction.commit()
            }
            else if (binding.detailFavouriteButton.drawable.constantState == requireContext().resources.getDrawable(android.R.drawable.btn_star_big_off).constantState){
                binding.detailFavouriteButton.setImageResource(android.R.drawable.btn_star_big_on)
                if(!favList.contains(binding.detailRestaurantName.text.toString())){
                    favoriteViewModel.addToFavorites(Favourites(index, binding.detailRestaurantName.text.toString(),username))
                    Toast.makeText(context, "Restaurant added to favorites", Toast.LENGTH_SHORT).show()
                    favList.add(binding.detailRestaurantName.text.toString())
                }
                val fragmentManager = fragmentManager
                val transaction = fragmentManager!!.beginTransaction()
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        return binding.root
    }

}