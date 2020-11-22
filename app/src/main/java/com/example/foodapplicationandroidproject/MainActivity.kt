package com.example.foodapplicationandroidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.foodapplicationandroidproject.databinding.ActivityMainBinding
import com.example.foodapplicationandroidproject.viewModels.ApiViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var bottomNavigationView: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        bottomNavigationView = binding.bottomNavMenu
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavigationView!!,
            navHostFragment!!.navController
        )
        bottomNavigationView!!.visibility = View.GONE
    }
}