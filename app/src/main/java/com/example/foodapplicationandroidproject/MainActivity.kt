package com.example.foodapplicationandroidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.foodapplicationandroidproject.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //variable for Databinding
    private lateinit var binding : ActivityMainBinding
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initializing databinding's variable
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        //defining navigation view
        bottomNavigationView = binding.bottomNavMenu
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavigationView!!,
            navHostFragment!!.navController
        )
        //bottom nav menu is not shown
        bottomNavigationView!!.visibility = View.GONE

    }
}