package com.lovoo.newsdesk.data.respository

import com.lovoo.newsdesk.data.model.HeadlineResult
import com.lovoo.newsdesk.data.respository.api.ApiCall
import com.lovoo.newsdesk.util.Globals
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by Arash on 9/15/2018.
 */
class NewsRepository @Inject constructor(){

    @Inject
    lateinit var api: ApiCall

    private var cachedResult: HeadlineResult? = null

    fun getNewsHeadlines(loadingSubject: BehaviorSubject<Boolean>): Observable<HeadlineResult?> {

        if(cachedResult == null){
            return api.getHeadlines(Globals.NEWS_API_KEY).doOnNext {
                cachedResult = it
                loadingSubject.onNext(false)
            }
        }else{
            return Observable.just(cachedResult)
                    .mergeWith(api.getHeadlines(Globals.NEWS_API_KEY).doOnNext {
                        cachedResult = it
                        loadingSubject.onNext(false)})
                    .doOnNext { cachedResult = it }
        }
    }

    private fun saveData(loadingSubject: BehaviorSubject<Boolean>){
    }
}