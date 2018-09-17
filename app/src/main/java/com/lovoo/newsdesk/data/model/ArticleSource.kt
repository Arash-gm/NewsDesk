package com.lovoo.newsdesk.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Arash on 9/14/2018.
 */
data class ArticleSource(@SerializedName("id") val id: String,
                         @SerializedName("name") val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()?:"",
            parcel.readString()?:"")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleSource> {
        override fun createFromParcel(parcel: Parcel): ArticleSource {
            return ArticleSource(parcel)
        }

        override fun newArray(size: Int): Array<ArticleSource?> {
            return arrayOfNulls(size)
        }
    }
}