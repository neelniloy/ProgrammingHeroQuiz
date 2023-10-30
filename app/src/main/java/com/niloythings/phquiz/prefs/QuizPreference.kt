package com.niloythings.phquiz.prefs

import android.content.Context
import android.content.SharedPreferences

class QuizPreference(context: Context) {
    private var preference: SharedPreferences
    private var editor: SharedPreferences.Editor
    private val scoreStatus = "score"
    init {
        preference = context.getSharedPreferences("quiz_pref", Context.MODE_PRIVATE)
        editor = preference.edit()
    }

    fun setHighScore(highScore: Int) {
        editor.putInt(scoreStatus, highScore)
        editor.commit()
    }

    fun getHighScore() : Int{
        return preference.getInt(scoreStatus, 0)
    }

}