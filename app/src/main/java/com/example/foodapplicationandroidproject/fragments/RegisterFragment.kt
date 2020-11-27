package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentRegisterBinding
import com.example.foodapplicationandroidproject.userdatabase.model.User
import com.example.foodapplicationandroidproject.userdatabase.viewModel.UserViewModel

class RegisterFragment : Fragment() {
    private lateinit var registerButton: Button
    private lateinit var mUserViewModel : UserViewModel
    private lateinit var binding: FragmentRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        registerButton = binding.registerPageRegisterButton
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        registerButton.setOnClickListener{
            if(!checkInputElements()){
                Toast.makeText(requireContext(), "All fields should be correct! Please check your inputs", Toast.LENGTH_LONG).show()
            }
            else{
                val user = checkIfUserAlreadyExists(binding.registerEmail.text.toString(),binding.registerUsername.text.toString(),binding.registerPhone.text.toString())
                if(user == 0){
                    addUserToDatabase(binding.registerFirstName.text.toString(),binding.registerLastName.text.toString(),binding.registerEmail.text.toString(),binding.registerUsername.text.toString(),binding.registerPhone.text.toString())
                    Toast.makeText(requireContext(), "Successful registration", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                else{
                    Toast.makeText(requireContext(), "User already exists!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    fun checkInputElements() : Boolean{
        when {
            binding.registerFirstName.text.toString().isEmpty() -> {
                binding.registerFirstName.error = "Empty input - please write a first name"
                return false
            }
            !isValidName(binding.registerFirstName.text.toString()) -> {
                binding.registerFirstName.error = "Invalid input - only letters are allowed"
                return false
            }
            binding.registerLastName.text.toString().isEmpty() -> {
                binding.registerLastName.error = "Empty input - please write a last name"
                return false
            }
            !isValidName(binding.registerLastName.text.toString()) -> {
                binding.registerLastName.error = "Invalid input - only letters are allowed"
                return false
            }
            binding.registerEmail.text.toString().isEmpty() -> {
                binding.registerEmail.error = "Empty input - please write an email"
                return false
            }
            !isValidEmail(binding.registerEmail.text.toString()) -> {
                binding.registerEmail.error = "Invalid input - please write a correct email"
                return false
            }
            binding.registerUsername.text.toString().isEmpty() -> {
                binding.registerUsername.error = "Empty input - please write a username"
                return false
            }
            binding.registerPhone.text.toString().isEmpty() -> {
                binding.registerPhone.error = "Empty input - please write a telephone number"
                return false
            }
            !isValidTelephone(binding.registerPhone.text.toString()) -> {
                binding.registerPhone.error = "Invalid phone number"
                return false
            }
            else -> return true
        }
    }

    fun isValidName(name : String) : Boolean{
        val regex = ("^[a-zA-Z ]+\$").toRegex()
        return regex.matches(name)
    }

    fun isValidEmail(email:String) : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidTelephone(phone : String) : Boolean{
        val regex = ("^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}\$").toRegex()
        return regex.matches(phone)
    }

    fun checkIfUserAlreadyExists(email: String,username:String,phone: String):Int{
        return Integer.parseInt(mUserViewModel.findUser(email,username,phone).toString())
    }

    fun addUserToDatabase(firstName : String, lastName : String, email: String, username: String, phone: String){
        val user = User(0,firstName,lastName,email,username,phone)
        mUserViewModel.addUser(user)
    }
}