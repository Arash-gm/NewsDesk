package com.lovoo.newsdesk.news

import com.lovoo.newsdesk.R
import com.lovoo.newsdesk.base.ApplicationClass
import com.lovoo.newsdesk.base.BaseViewModel
import com.lovoo.newsdesk.data.model.Country
import com.lovoo.newsdesk.data.model.HeadlineFilter
import com.lovoo.newsdesk.data.model.HeadlineResult
import com.lovoo.newsdesk.data.respository.NewsRepository
import com.lovoo.newsdesk.util.GlobalFunctions
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.text.SimpleDateFormat
import java.util.*
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

    fun getNewsArticles(headlineFilter: HeadlineFilter?): Observable<HeadlineResult?>{
        isLoadingSubject.onNext(true)
        return newsRepository.getNewsHeadlines(isLoadingSubject,headlineFilter)
    }

    fun provideCountryList(): Observable<ArrayList<Country>> {
        val countryMap: Map<String, String>? = GlobalFunctions.getHashMapResource(context,R.xml.country)
        val countries: ArrayList<Country> = ArrayList()

        countryMap?.let {
            for (item in countryMap.entries){
                var country = Country("",R.drawable.ic_gb)
                country.name = item.key
                country.drawableRes = GlobalFunctions.getDrawableResId(context,item.value)
                countries.add(country)
            }
        }
        return Observable.just(countries)
    }

    fun provideCategoryList(): Observable<ArrayList<String>> {
        val categories: ArrayList<String> = ArrayList()
        categories.addAll(context.resources.getStringArray(R.array.categories))
        return Observable.just(categories)
    }

    fun provideDateList(): Observable<ArrayList<String>> {
        val dates: ArrayList<String> = ArrayList()
        for(i in 0..7){
            val cal = GregorianCalendar.getInstance()
            cal.time = Date()
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, -i)
            dates.add(sdf.format(cal.time))
        }
        return Observable.just(dates)
    }

}