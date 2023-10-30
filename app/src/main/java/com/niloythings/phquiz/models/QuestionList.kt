package com.niloythings.phquiz.models
import com.google.gson.annotations.SerializedName

data class QuestionList(
    @SerializedName("questions" )
    var questions : List<Question> = arrayListOf()

)