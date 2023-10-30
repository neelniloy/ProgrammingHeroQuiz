package com.niloythings.phquiz.models

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("question")
    var question : String? = null,
    @SerializedName("answers")
    var answers : Answer? = Answer(),
    @SerializedName("questionImageUrl" )
    var questionImageUrl : String? = null,
    @SerializedName("correctAnswer")
    var correctAnswer : String? = null,
    @SerializedName("score")
    var score : Int? = null
)