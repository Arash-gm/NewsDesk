package com.lovoo.newsdesk.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Arash on 9/14/2018.
 */
data class HeadlineResult(@SerializedName("status") val status:String,
                          @SerializedName("totalResult") val totalResults: Int,
                          @SerializedName("articles") val articles: ArrayList<Article>): BaseResponse()