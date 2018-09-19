package com.lovoo.newsdesk.data.respository

import com.lovoo.newsdesk.base.ApplicationClass
import com.lovoo.newsdesk.data.model.HeadlineFilter
import com.lovoo.newsdesk.data.model.HeadlineResult
import com.lovoo.newsdesk.data.respository.api.ApiCall
import com.lovoo.newsdesk.util.GlobalFunctions
import com.lovoo.newsdesk.util.Globals
import com.orhanobut.hawk.Hawk
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by Arash on 9/15/2018.
 */
class NewsRepository @Inject constructor(){

    @Inject
    lateinit var api: ApiCall

    @Inject
    lateinit var context: ApplicationClass

    private var cachedResult: HeadlineResult? = null

    fun getNewsHeadlines(loadingSubject: BehaviorSubject<Boolean>, headlineFilter: HeadlineFilter?): Observable<HeadlineResult?> {

        when(GlobalFunctions.isConnectedToNetwork(context)){
            true -> return loadOnlineWithMemoryCache(loadingSubject,headlineFilter)
            false -> return loadFromPref(loadingSubject)
        }
    }

    private fun loadOnlineWithMemoryCache(loadingSubject: BehaviorSubject<Boolean>, headlineFilter: HeadlineFilter?): Observable<HeadlineResult?>{
        if(cachedResult == null){
            return api.getHeadlines(Globals.NEWS_API_KEY,headlineFilter?.country?.name,headlineFilter?.category).doOnNext {
                saveData(it)
                loadingSubject.onNext(false)
            }
        }else{
            return Observable.just(cachedResult)
                    .mergeWith(api.getHeadlines(Globals.NEWS_API_KEY,headlineFilter?.country?.name,headlineFilter?.category).doOnNext {
                        saveData(it)
                        loadingSubject.onNext(false)})
                    .doOnNext { cachedResult = it }
        }
    }

    private fun loadFromPref(loadingSubject: BehaviorSubject<Boolean>): Observable<HeadlineResult?>{
        cachedResult = Hawk.get(Globals.KEY_BUNDLE_CACHE_HEADLINES)
        loadingSubject.onNext(false)
        return Observable.just(cachedResult)
    }

    private fun saveData(it: HeadlineResult) {
        cachedResult = it
        Hawk.put(Globals.KEY_BUNDLE_CACHE_HEADLINES,cachedResult)
    }
}