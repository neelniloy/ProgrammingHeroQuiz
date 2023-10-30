package com.niloythings.phquiz.models

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("A")
    var A : String? = null,
    @SerializedName("B")
    var B : String? = null,
    @SerializedName("C")
    var C : String? = null,
    @SerializedName("D")
    var D : String? = null
)
