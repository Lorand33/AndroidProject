package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentProfileBinding
import com.example.foodapplicationandroidproject.favourites.FavouriteViewModel
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.email
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.name
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.phone
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.username
import com.example.foodapplicationandroidproject.fragments.MainScreenFragment.Companion.userFavouriteRestaurantList


class ProfileFragment : Fragment() {
    //variable for using Databinding on this fragment
    private lateinit var binding : FragmentProfileBinding
    //variable for the FavouriteViewModel
    private lateinit var favouriteViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initializing databinding's variable
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        /**
         * giving TextView element's a new value, using LoginFragment's companion object elements
         */
        binding.profileName.text= name
        binding.profileEmail.text = email
        binding.profilePhone.text = phone
        binding.profileUsername.text = username

        //providing a scope for the viewModel
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        //create the recycler view - which appears at the profile page
        //the adapter; parameters: user's favourite restaurants
        val listAdapter = FavouriteListAdapter(userFavouriteRestaurantList)
        //assign variable an element from the layouts
        val recyclerView = binding.favouriteRecyclerView
        //recycler view's adapter will the adapter defined previously
        recyclerView.adapter = listAdapter
        //recycler view's layout will be the context
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        return binding.root
    }

}