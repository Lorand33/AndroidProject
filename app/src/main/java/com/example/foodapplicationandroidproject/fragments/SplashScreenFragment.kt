package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentSplashScreenBinding
import java.util.*

/**
 * application's splash screen - the first page we see when the application starts
 */
class SplashScreenFragment : Fragment() {
    //variable for Databinding
    private lateinit var binding : FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initializing databinding's variable
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timer().schedule(object : TimerTask(){
            override fun run() {
                try {
                    synchronized(this) {
                    }
                }catch ( ex: InterruptedException){
                    ex.printStackTrace()
                }
                //we will be navigated to the login page
                findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
            }
        },2000)
    }

}