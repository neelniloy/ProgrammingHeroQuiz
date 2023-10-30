package com.niloythings.phquiz.repos

import com.niloythings.phquiz.models.QuestionList
import com.niloythings.phquiz.network.NetworkService

class QuizRepository {
    suspend fun fetchQuestionData(): QuestionList {
        val endUrl = "quiz.json"
        return NetworkService.quizServiceApi.getQuestionData(endUrl)
    }
}