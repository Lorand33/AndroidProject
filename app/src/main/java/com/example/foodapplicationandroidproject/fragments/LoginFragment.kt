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
    companion object{
        var name = ""
        var email = ""
        var phone = ""
        var username = ""
    }
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button
    private lateinit var showPassword : CheckBox
    private lateinit var passwordText: EditText
    private lateinit var usernameText : EditText
    private lateinit var mViewModel : UserViewModel
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        registerButton = binding.registerButton
        loginButton = binding.loginButton
        showPassword = binding.showPasswordLogin
        passwordText = binding.password
        usernameText = binding.username
        passwordText.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                activity,
                "The password should contain at least 8 characters, one number, one uppercase and one lowercase letter",
                Toast.LENGTH_LONG
            ).show()
        })
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        showPassword.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {

                passwordText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {

                passwordText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        })

        registerButton.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        loginButton.setOnClickListener{
            if(checkUser(usernameText.text.toString(),passwordText.text.toString())){
                val user = mViewModel.getUser(usernameText.text.toString(), passwordText.text.toString())
                name = "${user.firstName} ${user.lastName}"
                phone = user.telephone
                email = user.email
                username = user.username

                findNavController().navigate(R.id.action_loginFragment_to_mainScreenFragment)
            }
            else{
                Toast.makeText(requireContext(), "Wrong username or password!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    fun checkUser(username: String, password: String): Boolean{
        return mViewModel.loginUser(username, password) == 1
    }
}