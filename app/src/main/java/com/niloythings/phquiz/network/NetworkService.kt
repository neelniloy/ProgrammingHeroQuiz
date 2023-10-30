package com.niloythings.phquiz.network

import com.niloythings.phquiz.models.QuestionList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


const val base_url = "https://herosapp.nyc3.digitaloceanspaces.com/"


val retrofit = Retrofit.Builder()
    .baseUrl(base_url)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface QuizServiceApi {
    @GET()
    suspend fun getQuestionData(@Url endUrl: String): QuestionList

}

object NetworkService {
    val quizServiceApi: QuizServiceApi by lazy {
        retrofit.create(QuizServiceApi::class.java)
    }
}
