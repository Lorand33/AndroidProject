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
import com.example.foodapplicationandroidproject.database.repository.Repository
import com.example.foodapplicationandroidproject.database.viewModels.RestaurantViewModel
import com.example.foodapplicationandroidproject.database.viewModels.RestaurantViewModelFactory
import com.example.foodapplicationandroidproject.databinding.FragmentMainScreenBinding


class MainScreenFragment : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var mViewModel : RestaurantViewModel
    private var pageNumber : Int =1
    private var cityName : String = "New York"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main_screen, container, false)

        val repository = Repository()
        val viewModelFactory = RestaurantViewModelFactory(repository)
        mViewModel = ViewModelProvider(this,viewModelFactory).get(RestaurantViewModel::class.java)

        mViewModel.getRestaurantsByCity(cityName,pageNumber)
        mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{
            response -> if(response.isSuccessful){
                val listAdapter = RestaurantListAdapter(response.body()!!.restaurants)
                val recyclerView = binding.recycleView
                recyclerView.adapter = listAdapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.setHasFixedSize(true)
            }
        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<View>(R.id.bottom_nav_menu).visibility = View.VISIBLE

    }
}