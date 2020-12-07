package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentProfileBinding
import com.example.foodapplicationandroidproject.userdatabase.viewModel.UserViewModel



class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var mViewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        binding.profileName.text= arguments?.getString("NAME")
        binding.profileEmail.text = arguments?.getString("EMAIL")
        binding.profilePhone.text = arguments?.getString("PHONE")
        binding.profileUsername.text = arguments?.getString("USERNAME")

        return binding.root
    }

}