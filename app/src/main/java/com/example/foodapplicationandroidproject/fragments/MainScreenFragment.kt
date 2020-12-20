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
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.username

class MainScreenFragment() : Fragment() {
    //variable for Databinding
    private lateinit var binding: FragmentMainScreenBinding
    //variables for ViewModels; RestaurantViewModel and FavouriteViewModel
    private lateinit var mViewModel : RestaurantViewModel
    private lateinit var favouriteViewModel : FavouriteViewModel
    //variables for setting data which will be shown in this fragment
    private var pageNumber : Int =1
    private var cityName : String = "Addison"
    private var spinnerPosition = -1
    private lateinit var cities : List<String>

    companion object{
        //helper list for checking favourite button's state
        var favList : MutableList<String> = mutableListOf()
        //list to get current user's favourite restaurants
        lateinit var userFavouriteRestaurantList: List<Favourites>
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initializing databinding's variable
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main_screen, container, false)

        //create the ViewModelFactory for the RestaurantViewModel
        val repository = Repository()
        val viewModelFactory = RestaurantViewModelFactory(repository)
        //providing a scope for the viewModel(Restaurant)
        mViewModel = ViewModelProvider(this,viewModelFactory).get(RestaurantViewModel::class.java)
        //providing a scope for the viewModel(Favourite)
        favouriteViewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)

        /**
         * set from which city the restaurants should appear
         */
        //call the getCities function
        mViewModel.getCities()
        //we will check the response
        mViewModel.responseCities.observe(viewLifecycleOwner,{ response ->
            //if we get an answer for our call
            if(response.isSuccessful){
                //we get the cities from the response and we will save the cities to a variable
                cities = mViewModel.responseCities.value!!.body()!!.cities
                //set the spinner using adapter
                binding.citySpinner.adapter = ArrayAdapter(requireActivity().baseContext, android.R.layout.simple_spinner_item,cities)
                //if the spinner's position is not at the default value
                if(spinnerPosition != -1) {
                    //the city at the current position will be shown
                    binding.citySpinner.setSelection(spinnerPosition)
                }
                /**
                 * select what to happen if we select an item on the spinner
                 */
                binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    //if we select an item
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        //set the city's name using the position
                        cityName = cities[position]
                        //the first page will be shown
                        pageNumber = 1
                        //restaurants from the current city will be shown at the recycler view
                        mViewModel.getRestaurantsByCity(cityName,pageNumber)
                        //the other filter's text will be the city from the spinner
                        binding.searchCity.setText(cityName)
                        //if the previous selection was not the 0th element
                        if(parent?.selectedItemPosition != 0) {
                            //the spinner's position will be the last selection
                            spinnerPosition = parent!!.selectedItemPosition
                        }
                    }
                    //if we select nothing
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
        })

        /**
         * set the recycler view
         */
        //call the getRestaurantsByCity function
        mViewModel.getRestaurantsByCity(cityName,pageNumber)
        //we will check the response
        mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{ response ->
            //if we get answer for our call
            if(response.isSuccessful){
                //we get the current user's favourite restaurants
                userFavouriteRestaurantList = favouriteViewModel.getFavoritesByUser(username)
                //from the favourite restaurants we get its names
                for(restaurants in userFavouriteRestaurantList){
                    favList.add(restaurants.restaurantName)
                }
                //create the recycler view
                //the adapter; parameters: restaurants from the response and the context
                val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                //assign variable an element from the layouts
                val recyclerView = binding.recycleView
                //recycler view's adapter will the adapter defined previously
                recyclerView.adapter = listAdapter
                //recycler view's layout will be the context
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.setHasFixedSize(true)
                /**
                 * set the previous and next buttons visibility
                 */
                //if we are not at the last page -> the "Next" button will appear
                if(pageNumber*response.body()!!.per_page < response.body()!!.total_entries){
                    binding.listNextButton.visibility = View.VISIBLE
                }
                //in other case, the "Next" button will not be shown
                else{
                    binding.listNextButton.visibility = View.GONE
                }

                //if we are at the first page -> the "Previous" button does not appear
                if(pageNumber == 1){
                    binding.listPrevButton.visibility = View.GONE
                }
                //in any other case
                else{
                    //the button is shown
                    binding.listPrevButton.visibility = View.VISIBLE
                }

            }
        })

        /**
         * set what to happen we click at the "Search" button
         */
        binding.searchCityButton.setOnClickListener{
            //if we the filter's edittext is empty
            if(binding.searchCity.text.isEmpty()){
                //the following message will appear
                Toast.makeText(requireContext(),"Enter a city name", Toast.LENGTH_LONG).show()
            }
            //in other case
            else{
                //we want the first page
                pageNumber = 1
                //we get the city name from the filter
                cityName = binding.searchCity.text.toString()
                //we set the spinner to the city's position
                binding.citySpinner.setSelection(cities.indexOf(cityName))
                //we call the getRestaurantsByCity function
                mViewModel.getRestaurantsByCity(cityName,pageNumber)
                //we will check the response
                mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{ response ->
                    //if we get answer for our call
                    if(response.isSuccessful){
                        //create the recycler view
                        //the adapter; parameters: restaurants from the response and the context
                        val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                        //assign variable an element from the layouts
                        val recyclerView = binding.recycleView
                        //recycler view's adapter will the adapter defined previously
                        recyclerView.adapter = listAdapter
                        //recycler view's layout will be the context
                        recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        recyclerView.setHasFixedSize(true)
                        /**
                         * set the previous and next buttons visibility
                         */
                        //if we are not at the last page -> the "Next" button will appear
                        if(pageNumber*response.body()!!.per_page < response.body()!!.total_entries){
                            binding.listNextButton.visibility = View.VISIBLE
                        }
                        //in other case, the "Next" button will not be shown
                        else{
                            binding.listNextButton.visibility = View.GONE
                        }
                        //if we are at the first page -> the "Previous" button does not appear
                        if(pageNumber == 1){
                            binding.listPrevButton.visibility = View.GONE
                        }
                        //in any other case -> the button is shown
                        else{
                            binding.listPrevButton.visibility = View.VISIBLE
                        }
                    }
                })
                //a message will be displayed for a better orientation
                Toast.makeText(requireContext(),"Restaurants from $cityName", Toast.LENGTH_LONG).show()
            }
        }

        /**
         * set what to happen when we click on the input filter
         */
        binding.searchCity.setOnClickListener{
            //the edittext's content will be deleted
            binding.searchCity.text.clear()
        }

        /**
         * set what to happen when we press the Next button
         */
        binding.listNextButton.setOnClickListener{
            //the page number will be increased by one
            ++pageNumber
            //we call the getRestaurantsByCity function - with the new page number
            mViewModel.getRestaurantsByCity(cityName,pageNumber)
            //we will check the response
            mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{ response ->
                //if we get answer for our call
                if(response.isSuccessful) {
                    //create the recycler view
                    //the adapter; parameters: restaurants from the response and the context
                    val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                    //assign variable an element from the layouts
                    val recyclerView = binding.recycleView
                    //recycler view's adapter will the adapter defined previously
                    recyclerView.adapter = listAdapter
                    //recycler view's layout will be the context
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.setHasFixedSize(true)
                }
            })
            //a message will be displayed for a better orientation
            Toast.makeText(requireContext(),"$cityName page $pageNumber", Toast.LENGTH_SHORT).show()
        }

        /**
         * set what to happen when we press the Previous button
         */
        binding.listPrevButton.setOnClickListener{
            //the page number will be decreased by one
            --pageNumber
            //we call the getRestaurantsByCity function - with the new page number
            mViewModel.getRestaurantsByCity(cityName,pageNumber)
            //we will check the response
            mViewModel.responseRestaurantsFromCities.observe(viewLifecycleOwner,{ response ->
                //if we get answer for our call
                if(response.isSuccessful) {
                    //create the recycler view
                    //the adapter; parameters: restaurants from the response and the context
                    val listAdapter = RestaurantListAdapter(response.body()!!.restaurants,this.requireContext())
                    //assign variable an element from the layouts
                    val recyclerView = binding.recycleView
                    //recycler view's adapter will the adapter defined previously
                    recyclerView.adapter = listAdapter
                    //recycler view's layout will be the context
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.setHasFixedSize(true)
                }
            })
            //a message will be displayed for a better orientation
            Toast.makeText(requireContext(),"$cityName page $pageNumber", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    /**
     * when we reach this fragment, the bottom navigation menu will appear
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<View>(R.id.bottom_nav_menu).visibility = View.VISIBLE
    }
}