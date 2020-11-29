package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentMainScreenBinding
import com.example.foodapplicationandroidproject.database.repository.RestaurantRepository
import com.example.foodapplicationandroidproject.database.viewModels.RestaurantViewModel
import com.example.foodapplicationandroidproject.viewModels.ApiViewModel
import com.example.foodapplicationandroidproject.viewModels.ApiViewModelFactory


class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var mRestaurantViewModel : RestaurantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main_screen, container, false)

        val restaurantAdapter = RestaurantListAdapter()
        val recyclerView = binding.recycleView
        recyclerView.adapter = restaurantAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        mRestaurantViewModel.readAllData.observe(viewLifecycleOwner, Observer { restaurant -> restaurantAdapter.setData(restaurant) })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<View>(R.id.bottom_nav_menu).visibility = View.VISIBLE


    }
}