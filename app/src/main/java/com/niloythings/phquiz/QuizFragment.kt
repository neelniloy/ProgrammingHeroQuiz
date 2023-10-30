package com.niloythings.phquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.niloythings.phquiz.databinding.FragmentQuizBinding
import com.niloythings.phquiz.models.Question


class QuizFragment : Fragment() {
    private lateinit var binding:FragmentQuizBinding
    private var questionList = mutableListOf<Question>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        //Hide Actionbar as it is not necessary in this fragment
        (activity as AppCompatActivity).supportActionBar!!.hide()

        questionList.clear()
        arguments?.getParcelableArrayList<Question>("questionList")
            ?.let { questionList.addAll(it) }


        binding.cardOP1.setOnClickListener {
            if (true){
                binding.cardOP1.strokeColor = resources.getColor(R.color.green)
                binding.cardOP1.isClickable = false
            }else{
                binding.cardOP1.strokeColor = resources.getColor(R.color.red)
                binding.cardOP1.isClickable = false
            }
        }

        binding.cardOP2.setOnClickListener {
            if (true){
                binding.cardOP2.strokeColor = resources.getColor(R.color.green)
                binding.cardOP2.isClickable = false
            }else{
                binding.cardOP2.strokeColor = resources.getColor(R.color.red)
                binding.cardOP2.isClickable = false
            }
        }

        binding.cardOP3.setOnClickListener {
            if (true){
                binding.cardOP3.strokeColor = resources.getColor(R.color.green)
                binding.cardOP3.isClickable = false
            }else{
                binding.cardOP3.strokeColor = resources.getColor(R.color.red)
                binding.cardOP3.isClickable = false
            }
        }

        binding.cardOP4.setOnClickListener {
            if (true){
                binding.cardOP4.strokeColor = resources.getColor(R.color.green)
                binding.cardOP4.isClickable = false
            }else{
                binding.cardOP4.strokeColor = resources.getColor(R.color.red)
                binding.cardOP4.isClickable = false
            }
        }


        return binding.root
    }
}