package com.niloythings.phquiz

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.provider.Settings
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.niloythings.phquiz.databinding.CustomNoInternetDialogBinding
import com.niloythings.phquiz.databinding.FragmentQuizBinding
import com.niloythings.phquiz.databinding.QuizFinishDialogBinding
import com.niloythings.phquiz.models.Question
import com.niloythings.phquiz.prefs.QuizPreference
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class QuizFragment : Fragment() {
    private lateinit var binding:FragmentQuizBinding
    private var questionList = mutableListOf<Question>()
    private lateinit var preference: QuizPreference
    private var questionCount = 0
    private var score = 0
    private var totalScore = 0
    private var timer: CountDownTimer? = null
    private var correctCount = 0
    private var inCorrectCount = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        //Hide Actionbar as it is not necessary in this fragment
        (activity as AppCompatActivity).supportActionBar!!.hide()

        preference = QuizPreference(requireContext())

        questionList.clear()
        arguments?.getParcelableArrayList<Question>("questionList")
            ?.let { questionList.addAll(it) }

        Log.d("TAG", "questionList2: $questionList")


        //set question
        setQuestion()

        binding.cardOP1.setOnClickListener {
            onAnswerSelected("A")
        }

        binding.cardOP2.setOnClickListener {
            onAnswerSelected("B")
        }

        binding.cardOP3.setOnClickListener {
            onAnswerSelected("C")
        }

        binding.cardOP4.setOnClickListener {
            onAnswerSelected("D")
        }

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setQuestion() {

        //Start Time
        startCountdown()

        // Create a list of the CardViews
        val cardViews = listOf(binding.cardOP1, binding.cardOP2, binding.cardOP3, binding.cardOP4)

        // Shuffle the list of CardViews
        val shuffledCardViews = cardViews.shuffled()

        // Clear the LinearLayout before adding CardViews in the new order
        binding.optionLayout.removeAllViews()

        // Add the CardViews to the LinearLayout in the shuffled order
        shuffledCardViews.forEach {
            binding.optionLayout.addView(it)
        }

        //Reset Options Background
        binding.cardOP1.strokeColor = resources.getColor(R.color.ash)
        binding.cardOP1.isClickable = true
        binding.cardOP2.strokeColor = resources.getColor(R.color.ash)
        binding.cardOP2.isClickable = true
        binding.cardOP3.strokeColor = resources.getColor(R.color.ash)
        binding.cardOP3.isClickable = true
        binding.cardOP4.strokeColor = resources.getColor(R.color.ash)
        binding.cardOP4.isClickable = true

        //set question count
        binding.questionCount.text = "Question: ${questionCount+1}/${questionList.size}"

        //set score
        binding.score.text = "Score: ${totalScore}"

        score = questionList[questionCount].score!!
        binding.questionTVPoint.text = "${questionList[questionCount].score} Point"
        binding.questionTV.text = Html.fromHtml("${questionList[questionCount].question}", Html.FROM_HTML_MODE_COMPACT)

        val textA = questionList[questionCount].answers!!.A
        if (!textA.isNullOrEmpty()){
            binding.cardOP1.visibility = View.VISIBLE
            binding.OP1.text = textA
        }else{
            binding.cardOP1.visibility = View.GONE
        }

        val textB = questionList[questionCount].answers!!.B
        if (!textB.isNullOrEmpty()){
            binding.cardOP2.visibility = View.VISIBLE
            binding.OP2.text = textB
        }else{
            binding.cardOP2.visibility = View.GONE
        }

        val textC = questionList[questionCount].answers!!.C
        if (!textC.isNullOrEmpty()){
            binding.cardOP3.visibility = View.VISIBLE
            binding.OP3.text = textC
        }else{
            binding.cardOP3.visibility = View.GONE
        }

        val textD = questionList[questionCount].answers!!.D
        if (!textD.isNullOrEmpty()){
            binding.cardOP4.visibility = View.VISIBLE
            binding.OP4.text = textD
        }else{
            binding.cardOP4.visibility = View.GONE
        }

        if (questionList[questionCount].questionImageUrl!=null && questionList[questionCount].questionImageUrl!="null"){
            binding.questionImage.visibility = View.VISIBLE
            Glide.with(requireActivity())
                .load(questionList[questionCount].questionImageUrl)
                .into(binding.questionImage)
        }else{
            binding.questionImage.visibility = View.GONE
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun calculateScore(correctAnswer:Boolean) {

        //Make sure not click twice
        binding.cardOP1.isClickable = false
        binding.cardOP2.isClickable = false
        binding.cardOP3.isClickable = false
        binding.cardOP4.isClickable = false

        //Move to the next question
        questionCount++

        if (correctAnswer){
            totalScore+=score
            binding.score.text = "Score: ${totalScore}"
        }

        lifecycleScope.launch {
            delay(2000L)

            if (questionCount==questionList.size){
                val temp = preference.getHighScore()
                if (temp<totalScore){
                    preference.setHighScore(totalScore)
                    showQuizFinishDialog()
                }else{
                    showQuizFinishDialog()
                }
            }else{
                setQuestion()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun onAnswerSelected(selectedAnswer: String) {

        //Cancel Timer
        timer?.cancel()

        //Make sure not click twice
        binding.cardOP1.isClickable = false
        binding.cardOP2.isClickable = false
        binding.cardOP3.isClickable = false
        binding.cardOP4.isClickable = false

        val correctAnswer = questionList[questionCount].correctAnswer

        if (selectedAnswer == correctAnswer) {
            when(selectedAnswer){
                "A"->{
                    binding.cardOP1.strokeColor = resources.getColor(R.color.green)
                }
                "B"->{
                    binding.cardOP2.strokeColor = resources.getColor(R.color.green)
                }
                "C"->{
                    binding.cardOP3.strokeColor = resources.getColor(R.color.green)
                }
                "D"->{
                    binding.cardOP4.strokeColor = resources.getColor(R.color.green)
                }
            }
            calculateScore(true)
            Toast.makeText(context, "Correct Answer!", Toast.LENGTH_SHORT).show()
            correctCount++
        } else {
            when(selectedAnswer){
                "A"->{
                    binding.cardOP1.strokeColor = resources.getColor(R.color.red)
                }
                "B"->{
                    binding.cardOP2.strokeColor = resources.getColor(R.color.red)
                }
                "C"->{
                    binding.cardOP3.strokeColor = resources.getColor(R.color.red)
                }
                "D"->{
                    binding.cardOP4.strokeColor = resources.getColor(R.color.red)
                }
            }

            when(correctAnswer){
                "A"->{
                    binding.cardOP1.strokeColor = resources.getColor(R.color.green)
                }
                "B"->{
                    binding.cardOP2.strokeColor = resources.getColor(R.color.green)
                }
                "C"->{
                    binding.cardOP3.strokeColor = resources.getColor(R.color.green)
                }
                "D"->{
                    binding.cardOP4.strokeColor = resources.getColor(R.color.green)
                }
            }
            calculateScore(false)
            Toast.makeText(context, "Incorrect Answer!", Toast.LENGTH_SHORT).show()
            inCorrectCount++
        }
    }

    private fun startCountdown() {
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                binding.countDownTimer.text = "$secondsRemaining"
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onFinish() {
                calculateScore(false)
                inCorrectCount++
            }
        }
        timer?.start()
    }

    private fun showQuizFinishDialog() {

        val dialogBinding = QuizFinishDialogBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root
        val dialogBuilder = AlertDialog.Builder(requireActivity())
            .setView(dialogView)
            .setCancelable(false)

        val alertDialog = dialogBuilder.create()

        dialogBinding.totalQuestion.text = "${questionList.size}"
        dialogBinding.correct.text = "${correctCount}"
        dialogBinding.incorrect.text = "${inCorrectCount}"
        dialogBinding.currentScore.text = "${totalScore}"
        dialogBinding.allTimeBest.text = "${preference.getHighScore()}"

        // Define the action when the home button is clicked
        dialogBinding.homeBtn.setOnClickListener {
            alertDialog?.dismiss()
            findNavController().popBackStack()
        }

        alertDialog?.show()
    }
}