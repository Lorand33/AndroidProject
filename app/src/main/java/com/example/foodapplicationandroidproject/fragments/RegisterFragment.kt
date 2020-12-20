package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentRegisterBinding
import com.example.foodapplicationandroidproject.userdatabase.model.User
import com.example.foodapplicationandroidproject.userdatabase.viewModel.UserViewModel

class RegisterFragment : Fragment() {
    //variable for the Register button
    private lateinit var registerButton: Button
    //variable for ViewModel - UserViewModel
    private lateinit var mUserViewModel : UserViewModel
    //variable for Databinding
    private lateinit var binding: FragmentRegisterBinding
    //variable for the checkbox
    private lateinit var showPassword : CheckBox
    //variable for password's EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initializing databinding's variable
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)

        //assign variable's an element from the layouts
        registerButton = binding.registerPageRegisterButton
        showPassword = binding.showPassword
        password = binding.registerPassword

        /**
         * set what to happen when the user clicks on password's EditText at the login
         */
        password.setOnClickListener{
            //information about the password's type will be given
            //this should contain at least 8 characters, one number, one uppercase and one lowercase letter
            Toast.makeText(
                    activity,
                    "The password should contain at least 8 characters, one number, one uppercase and one lowercase letter",
                    Toast.LENGTH_LONG
            ).show()
        }

        /**
         * set what happens when the user checks in or out the checkbox
         */
        showPassword.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                //the password will be shown
                password.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                //the password will be hidden
                password.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })

        //providing a scope for the viewModel
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        /**
         * set what to happen when the user clicks on "Register" button
         */
        registerButton.setOnClickListener{
            //we will check if everything was completed correctly
            //if something was not correct
            if(!checkInputElements()){
                //this message will appear
                Toast.makeText(requireContext(), "All fields should be correct! Please check your inputs", Toast.LENGTH_LONG).show()
            }
            //if everything was correct
            else{
                //check if user already exists
                //if the number of appearances is 0
                if(checkUser(binding.registerEmail.text.toString(),binding.registerUsername.text.toString(),binding.registerPhone.text.toString())) {
                    //we will add the user to the database
                    addUserToDatabase(binding.registerFirstName.text.toString(), binding.registerLastName.text.toString(), binding.registerEmail.text.toString(),
                        binding.registerUsername.text.toString(), binding.registerPhone.text.toString(), binding.registerPassword.text.toString())
                    //the following message will appear
                    Toast.makeText(requireContext(), "Successful registration", Toast.LENGTH_SHORT).show()
                    //we will be navigated to the login page
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                else{
                    //the following message will appear
                    Toast.makeText(requireContext(), "This user is already registered!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    /**
     * function to check if the input data is correct
     */
    private fun checkInputElements() : Boolean{
        when {
            //if we didn't write a first name
            //false will be returned and the following message will be shown
            binding.registerFirstName.text.toString().isEmpty() -> {
                binding.registerFirstName.error = "Empty input - please write a first name"
                return false
            }
            //if we didn't write a valid first name
            //false will be returned and the following message will be shown
            !isValidName(binding.registerFirstName.text.toString()) -> {
                binding.registerFirstName.error = "Invalid input - only letters are allowed"
                return false
            }
            //if we didn't write a last name
            //false will be returned and the following message will be shown
            binding.registerLastName.text.toString().isEmpty() -> {
                binding.registerLastName.error = "Empty input - please write a last name"
                return false
            }
            //if we didn't write a valid last name
            //false will be returned and the following message will be shown
            !isValidName(binding.registerLastName.text.toString()) -> {
                binding.registerLastName.error = "Invalid input - only letters are allowed"
                return false
            }
            //if we didn't write an email
            //false will be returned and the following message will be shown
            binding.registerEmail.text.toString().isEmpty() -> {
                binding.registerEmail.error = "Empty input - please write an email"
                return false
            }
            //if we didn't write a valid email
            //false will be returned and the following message will be shown
            !isValidEmail(binding.registerEmail.text.toString()) -> {
                binding.registerEmail.error = "Invalid input - please write a correct email"
                return false
            }
            //if we didn't write a username
            //false will be returned and the following message will be shown
            binding.registerUsername.text.toString().isEmpty() -> {
                binding.registerUsername.error = "Empty input - please write a username"
                return false
            }
            //if we didn't write a a phone number
            //false will be returned and the following message will be shown
            binding.registerPhone.text.toString().isEmpty() -> {
                binding.registerPhone.error = "Empty input - please write a telephone number"
                return false
            }
            //if we didn't write a valid phone number
            //false will be returned and the following message will be shown
            !isValidTelephone(binding.registerPhone.text.toString()) -> {
                binding.registerPhone.error = "Invalid phone number"
                return false
            }
            //if we didn't write a password
            //false will be returned and the following message will be shown
            binding.registerPassword.text.toString().isEmpty() -> {
                binding.registerPassword.error = "Empty input - please write your password"
                return false
            }
            //if we didn't write a valid password
            //false will be returned and the following message will be shown
            !isValidPassword(binding.registerPassword.text.toString()) -> {
                binding.registerPassword.error = "Invalid password - please write a correct one"
                return false
            }
            //in any other cases - true will be returned
            else -> return true
        }
    }

    //function to check if the first name or the last name is correct -> only letters allowed
    private fun isValidName(name : String) : Boolean{
        val regex = ("^[a-zA-Z ]+\$").toRegex()
        return regex.matches(name)
    }

    //function to check if email is correct
    private fun isValidEmail(email:String) : Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    //function to check if phone number is correct
    private fun isValidTelephone(phone : String) : Boolean{
        val regex = ("^(\\+4|)?(07[0-8]{1}[0-9]{1}|02[0-9]{2}|03[0-9]{2}){1}?(\\s|\\.|\\-)?([0-9]{3}(\\s|\\.|\\-|)){2}\$").toRegex()
        return regex.matches(phone)
    }

    //add a new user to the database
    private fun addUserToDatabase(firstName: String, lastName: String, email: String, username: String, telephone: String, password: String){
        val user = User(firstName, lastName, email, username, telephone, password)
        mUserViewModel.addUser(user)
    }

    //function to check if the password is correct
    private fun isValidPassword(password: String):Boolean{
        val regex =("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}\$").toRegex()
        return regex.matches(password)
    }

    //check if the user already exists
    //if the number is 0 -> the registration can be made
    private fun checkUser(email: String, username: String, phone: String): Boolean{
        val user = mUserViewModel.findUser(email, username, phone)
        return user == 0
    }
}