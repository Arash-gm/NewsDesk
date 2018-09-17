package com.lovoo.newsdesk.newsdetail

import com.lovoo.newsdesk.base.ApplicationClass
import com.lovoo.newsdesk.base.BaseViewModel
import com.lovoo.newsdesk.data.model.Article
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by Arash on 9/15/2018.
 */
class NewsDetailViewModel @Inject constructor(): BaseViewModel() {

    @Inject
    lateinit var context: ApplicationClass

    private var article: Article? = null

    fun providePageTitle(): Observable<String> {
        return Observable.just(article?.articleSource?.name)
    }

    fun provideMainImage(): Observable<String> {
        return Observable.just(article?.imageUrl)
    }

    fun provideHeadline(): Observable<String> {
        return Observable.just(article?.title)
    }

    fun provideDesc(): Observable<String> {
        return Observable.just(article?.desc)
    }

    fun setLoadedArticle(loadedArticle: Article){
        article = loadedArticle
    }

}