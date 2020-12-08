package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentProfileBinding
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.email
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.name
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.phone
import com.example.foodapplicationandroidproject.fragments.LoginFragment.Companion.username


class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        binding.profileName.text= name
        binding.profileEmail.text = email
        binding.profilePhone.text = phone
        binding.profileUsername.text = username

        return binding.root
    }

}