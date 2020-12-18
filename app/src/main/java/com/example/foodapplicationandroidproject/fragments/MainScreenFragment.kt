package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.restaurants.repository.Repository
import com.example.foodapplicationandroidproject.restaurants.viewModels.RestaurantViewModel
import com.example.foodapplicationandroidproject.restaurants.viewModels.RestaurantViewModelFactory
import com.example.foodapplicationandroidproject.databinding.FragmentMainScreenBinding
import com.example.foodapplicationandroidproject.favourites.FavouriteViewModel
import com.example.foodapplicationandroidproject.favourites.Favourites


class MainScreenFragment() : Fragment() {
    private lateinit var binding: FragmentMainScreenBinding
    private lateinit var mViewModel : RestaurantViewModel
    private lateinit var favouriteViewModel : FavouriteViewModel
    private var pageNumber : Int =1
    private var cityName : String = "Addison"
    private var spinnerPosition = -1
    private lateinit var cities : List<String>

    companion object{
        var favList : MutableList<String> = mutableListOf()
        lateinit var favouriteRestaurantList: List<Favourites>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main_screen, container, false)

        val repository = Repository()
        val viewModelFactory = RestaurantViewModelFactory(repository)
        mViewModel = ViewModelProvider(this,viewModelFactory).get(RestaurantViewModel::class.java)
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)


        mViewModel.getCities()
        mViewModel.responseCities.observe(viewLifecycleOwner,{
            response -> if(response.isSuccessful){
                cities = mViewModel.responseCities.value!!.body()!!.cities
                binding.citySpinner.adapter = ArrayAdapter(requireActivity().baseContext, android.R.layout.simple_spinner_item,cities)
                if(spinnerPosition != -1) {
                    binding.citySpinner.setSelection(spinnerPosition)
                }
                binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        cityName = cities[position]
                        pageNumber = 1
                        mViewModel.getRestaurantsByCity(cityName,pageNumber)
                        binding.searchCity.setText(cityName)
                        if(parent?.selectedItemPosition != 0) {
                            spinnerPosition = parent!!.selectedItemPosition
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        })

        mViewModel.getRestaurantsByCity(cityName,pageNumber)
        mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{ response ->
            if(response.isSuccessful){
                favouriteRestaurantList = favouriteViewModel.getAllFavorites()
                for(restaurants in favouriteRestaurantList){
                    favList.add(restaurants.restaurantName)
                }
                val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                val recyclerView = binding.recycleView
                recyclerView.adapter = listAdapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.setHasFixedSize(true)
                if(pageNumber*response.body()!!.per_page < response.body()!!.total_entries){
                    binding.listNextButton.visibility = View.VISIBLE
                }
                else{
                    binding.listNextButton.visibility = View.GONE
                }

                if(pageNumber == 1){
                    binding.listPrevButton.visibility = View.GONE
                }
                else{
                    binding.listPrevButton.visibility = View.VISIBLE
                }

            }
        })

        binding.searchCityButton.setOnClickListener{
            if(binding.searchCity.text.isEmpty()){
                Toast.makeText(requireContext(),"Enter a city name", Toast.LENGTH_LONG).show()
            }
            else{
                pageNumber = 1
                cityName = binding.searchCity.text.toString()
                binding.citySpinner.setSelection(cities.indexOf(cityName))
                mViewModel.getRestaurantsByCity(cityName,pageNumber)
                mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{ response ->
                    if(response.isSuccessful){
                        val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                        val recyclerView = binding.recycleView
                        recyclerView.adapter = listAdapter
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.setHasFixedSize(true)
                        if(pageNumber*response.body()!!.per_page < response.body()!!.total_entries){
                            binding.listNextButton.visibility = View.VISIBLE
                        }
                        else{
                            binding.listNextButton.visibility = View.GONE
                        }

                        if(pageNumber == 1){
                            binding.listPrevButton.visibility = View.GONE
                        }
                        else{
                            binding.listPrevButton.visibility = View.VISIBLE
                        }
                    }
                })
                Toast.makeText(requireContext(),"Restaurants from $cityName", Toast.LENGTH_LONG).show()
            }
        }

        binding.searchCity.setOnClickListener{
            binding.searchCity.text.clear()
        }

        binding.listNextButton.setOnClickListener{
            ++pageNumber
            mViewModel.getRestaurantsByCity(cityName,pageNumber)
            mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{
                response -> if(response.isSuccessful) {
                val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                val recyclerView = binding.recycleView
                recyclerView.adapter = listAdapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.setHasFixedSize(true)
            }
            })
            Toast.makeText(requireContext(),"$cityName page $pageNumber", Toast.LENGTH_SHORT).show()
        }

        binding.listPrevButton.setOnClickListener{
            --pageNumber
            mViewModel.getRestaurantsByCity(cityName,pageNumber)
            mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{
                response -> if(response.isSuccessful) {
                val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                val recyclerView = binding.recycleView
                recyclerView.adapter = listAdapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.setHasFixedSize(true)
            }
            })
            Toast.makeText(requireContext(),"$cityName page $pageNumber", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<View>(R.id.bottom_nav_menu).visibility = View.VISIBLE

    }
}