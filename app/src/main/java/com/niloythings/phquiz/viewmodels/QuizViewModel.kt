package com.niloythings.phquiz.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niloythings.phquiz.models.QuestionList
import com.niloythings.phquiz.repos.QuizRepository
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel(){
    val repository = QuizRepository()
    val questionLiveData: MutableLiveData<QuestionList> = MutableLiveData()

    fun fetchQuestionData() {
        viewModelScope.launch {
            try {
                questionLiveData.value = repository.fetchQuestionData()
            }catch (e: Exception) {
                e.localizedMessage?.let { Log.e("LocationViewModel", it) }
            }
        }
    }
}

