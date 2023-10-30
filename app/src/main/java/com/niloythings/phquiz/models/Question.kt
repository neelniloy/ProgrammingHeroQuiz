package com.niloythings.phquiz.models

import android.os.Parcel
import android.os.Parcelable
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
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Answer::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as Int?
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeParcelable(answers, flags)
        parcel.writeString(questionImageUrl)
        parcel.writeString(correctAnswer)
        parcel.writeValue(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}