package com.niloythings.phquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.niloythings.phquiz.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {
    private lateinit var binding:FragmentQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        //Hide Actionbar as it is not necessary in this fragment
        (activity as AppCompatActivity).supportActionBar!!.hide()

        return binding.root
    }
}