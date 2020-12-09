package com.example.foodapplicationandroidproject.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail,container,false)

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
        return binding.root
    }

}