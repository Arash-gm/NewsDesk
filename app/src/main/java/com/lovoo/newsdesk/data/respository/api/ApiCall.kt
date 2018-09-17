package com.lovoo.newsdesk.data.respository.api

import com.lovoo.newsdesk.data.model.HeadlineResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Arash on 9/14/2018.
 */
interface ApiCall{

    @GET("top-headlines?q=en&pagesize=30")
    fun getHeadlines(@Query("apiKey") apiKey:String): Observable<HeadlineResult>

}