package com.lovoo.newsdesk.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Arash on 9/14/2018.
 */
data class Article(@SerializedName("source") val articleSource: ArticleSource,
                   @SerializedName("author") val author: String,
                   @SerializedName("title") val title: String,
                   @SerializedName("description") val desc: String,
                   @SerializedName("url") val url: String,
                   @SerializedName("urlToImage") val imageUrl: String,
                   @SerializedName("publishedAt") val publishedAt: String,
                   @SerializedName("content") val content: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(ArticleSource::class.java.classLoader),
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"",
            parcel.readString()?:"")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(articleSource, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(url)
        parcel.writeString(imageUrl)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}