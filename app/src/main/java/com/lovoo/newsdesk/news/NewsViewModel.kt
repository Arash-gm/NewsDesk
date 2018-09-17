package com.lovoo.newsdesk.news

import com.lovoo.newsdesk.R
import com.lovoo.newsdesk.base.ApplicationClass
import com.lovoo.newsdesk.base.BaseViewModel
import com.lovoo.newsdesk.data.model.HeadlineResult
import com.lovoo.newsdesk.data.respository.NewsRepository
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by Arash on 9/15/2018.
 */
class NewsViewModel @Inject constructor(): BaseViewModel() {

    @Inject
    lateinit var newsRepository: NewsRepository

    @Inject
    lateinit var context: ApplicationClass

    private var isLoadingSubject = BehaviorSubject.create<Boolean>()

    fun providePageTitle(): String{
        return context.resources.getString(R.string.headlines_title)
    }

    fun isLoadingObservable(): Observable<Boolean> {
        return isLoadingSubject
    }

    fun getNewsArticles(): Observable<HeadlineResult?>{
        isLoadingSubject.onNext(true)
        return newsRepository.getNewsHeadlines(isLoadingSubject)
    }

}