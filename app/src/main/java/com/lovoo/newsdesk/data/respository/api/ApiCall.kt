package com.lovoo.newsdesk.data.respository.api

import com.lovoo.newsdesk.data.model.HeadlineResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Arash on 9/14/2018.
 */
interface ApiCall{

    @GET("top-headlines?language=en&pagesize=60")
    fun getHeadlines(@Query("apiKey") apiKey:String, @Query("country") country: String?): Observable<HeadlineResult>

}