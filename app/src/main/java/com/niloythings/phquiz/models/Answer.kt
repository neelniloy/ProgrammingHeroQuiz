package com.niloythings.phquiz.models

import android.os.Parcel
import android.os.Parcelable
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
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(A)
        parcel.writeString(B)
        parcel.writeString(C)
        parcel.writeString(D)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Answer> {
        override fun createFromParcel(parcel: Parcel): Answer {
            return Answer(parcel)
        }

        override fun newArray(size: Int): Array<Answer?> {
            return arrayOfNulls(size)
        }
    }
}
