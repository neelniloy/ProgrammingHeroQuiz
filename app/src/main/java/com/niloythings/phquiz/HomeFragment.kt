package com.niloythings.phquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.niloythings.phquiz.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(inflater, container, false)
        //Hide Actionbar as it is not necessary in this fragment
        (activity as AppCompatActivity).supportActionBar!!.hide()

        binding.btnStartQuiz.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quizFragment)
        }

        return binding.root
    }
}