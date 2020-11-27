package com.example.foodapplicationandroidproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.foodapplicationandroidproject.R
import com.example.foodapplicationandroidproject.databinding.FragmentMainScreenBinding
import com.example.foodapplicationandroidproject.repository.Repository
import com.example.foodapplicationandroidproject.viewModels.ApiViewModel
import com.example.foodapplicationandroidproject.viewModels.ApiViewModelFactory

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MainScreenFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var apiViewModel: ApiViewModel
    private lateinit var binding: FragmentMainScreenBinding
    var counter =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main_screen, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       requireActivity().findViewById<View>(R.id.bottom_nav_menu).visibility = View.VISIBLE

        val repository = Repository()
        val viewModelFactory = ApiViewModelFactory(repository)
        apiViewModel = ViewModelProvider(requireActivity(),viewModelFactory).get(ApiViewModel::class.java)

    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}