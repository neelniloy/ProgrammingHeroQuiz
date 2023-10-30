package com.niloythings.phquiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.niloythings.phquiz.databinding.FragmentHomeBinding
import com.niloythings.phquiz.models.Question
import com.niloythings.phquiz.prefs.QuizPreference
import com.niloythings.phquiz.viewmodels.QuizViewModel

class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private val quizViewModel: QuizViewModel by activityViewModels()
    private var questionList = mutableListOf<Question>()
    private lateinit var preference: QuizPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentHomeBinding.inflate(inflater, container, false)
        //Hide Actionbar as it is not necessary in this fragment
        (activity as AppCompatActivity).supportActionBar!!.hide()
        preference = QuizPreference(requireContext())

        binding.highScoreTv.text = "${preference.getHighScore()} Point"

        //get question list
        quizViewModel.fetchQuestionData().observe(viewLifecycleOwner){list->
            questionList.clear()
            questionList.addAll(list.questions)
            Log.d("TAG", "questionList: $questionList")
        }
        binding.btnStartQuiz.setOnClickListener {

            if (questionList.isEmpty()){
                Toast.makeText(requireActivity(), "Quiz data is fetching...", Toast.LENGTH_SHORT).show()
            }else{
                val bundle = Bundle()
                val questionModelList = ArrayList<Question>()
                questionModelList.addAll(questionList)
                // Put the list in the bundle
                bundle.putParcelableArrayList("questionList", questionModelList)

                findNavController().navigate(R.id.action_homeFragment_to_quizFragment,bundle)
            }
        }

        return binding.root
    }

    override fun onResume() {
        binding.highScoreTv.text = "${preference.getHighScore()} Point"
        super.onResume()
    }
}