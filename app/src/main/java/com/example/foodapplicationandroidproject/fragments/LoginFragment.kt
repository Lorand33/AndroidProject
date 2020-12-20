package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentLoginBinding
import com.example.foodapplicationandroidproject.userdatabase.viewModel.UserViewModel

class LoginFragment : Fragment() {
    //elements used at different parts of the project
    companion object{
        var name = ""
        var email = ""
        var phone = ""
        var username = ""
    }
    //defining variables, all of them will be initialized later
    //buttons
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    //checkbox
    private lateinit var showPassword : CheckBox
    //edittexts
    private lateinit var passwordText: EditText
    private lateinit var usernameText : EditText
    //ViewModel - UserViewModel
    private lateinit var mViewModel : UserViewModel
    //databinding
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initializing databinding's variable
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        //initializing the variables using databinding
        registerButton = binding.registerButton
        loginButton = binding.loginButton
        showPassword = binding.showPasswordLogin
        passwordText = binding.password
        usernameText = binding.username

        /**
         * set what to happen when the user clicks on password's edittext at the login
         */
        passwordText.setOnClickListener{
            //information about the password's type will be given
            //this should contain at least 8 characters, one number, one uppercase and one lowercase letter
            Toast.makeText(
                activity,
                "The password should contain at least 8 characters, one number, one uppercase and one lowercase letter",
                Toast.LENGTH_LONG
            ).show()
        }

        //providing a scope for the viewModel
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        /**
         * set what happens when the user checks in or out the checkbox
         */
        showPassword.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                //the password will be shown
                passwordText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                //the password will be hidden
                passwordText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })

        /**
         * set what to happen when the user clicks on "Register" button
         */
        registerButton.setOnClickListener{
            //the user will be navigated to the registration page
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        /**
         * set what to happen when the user clicks on "Login" button
         */
        loginButton.setOnClickListener{
            //we will start a function to check if everything was completed correctly
            //if something was not correct
            if(!checkInputElements()){
                //this message will appear
                Toast.makeText(requireContext(), "All fields should be correct! Please check your inputs", Toast.LENGTH_LONG).show()
            }
            //if everything was correct
            else {
                //check if user already exists
                //if the number of appearances is 1
                if (checkUser(usernameText.text.toString(), passwordText.text.toString())) {
                    //we will get the user's data from the database
                    val user = mViewModel.getUser(usernameText.text.toString(), passwordText.text.toString())
                    //we will set give values for elements from the companion object
                    name = "${user.firstName} ${user.lastName}"
                    phone = user.telephone
                    email = user.email
                    username = user.username

                    //we will be navigated to the main screen
                    findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
                //in other case
                } else {
                    //the following message will appear
                    Toast.makeText(requireContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }

    /**
     * check the number of appearances
     * we will use a function from the UserViewModel for this
     */
    private fun checkUser(username: String, password: String): Boolean{
        return mViewModel.loginUser(username, password) == 1
    }

    /**
     * function to check if the input data is correct
     */
    private fun checkInputElements() : Boolean {
        when {
            //if we didn't write a username
            //false will be returned and the following message will be shown
            usernameText.text.toString().isEmpty() -> {
                usernameText.error = "Empty input - please write a username"
                return false
            }
            //if the username is not correct
            //false will be returned and the following message will be shown
            !isValidUsername(usernameText.text.toString()) -> {
                usernameText.error = "Invalid input - please write a correct username"
                return false
            }
            //if we didn't write a password
            //false will be returned and the following message will be shown
            passwordText.text.toString().isEmpty() -> {
                passwordText.error = "Empty input - please write a password"
                return false
            }
            //if the password is not correct
            //false will be returned and the following message will be shown
            !isValidPassword(passwordText.text.toString()) -> {
                passwordText.error = "Invalid input - please write a correct password"
                return false
            }
            //in other cases -> true will be returned
            else -> return true
        }
    }

    //function to check if the username is valid
    private fun isValidUsername(username : String) : Boolean{
        val regex = ("^[a-zA-Z0-9 ]+\$").toRegex()
        return regex.matches(username)
    }

    //function to check if the password is valid
    private fun isValidPassword(password: String):Boolean{
        val regex =("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}\$").toRegex()
        return regex.matches(password)
    }
}