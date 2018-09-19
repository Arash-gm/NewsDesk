package com.lovoo.newsdesk.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import com.lovoo.newsdesk.R
import com.lovoo.newsdesk.base.BaseActivity
import com.lovoo.newsdesk.data.model.Country
import com.lovoo.newsdesk.data.model.HeadlineResult
import com.lovoo.newsdesk.newsdetail.NewsDetailActivity
import com.lovoo.newsdesk.util.GlobalFunctions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class NewsActivity : BaseActivity(), View.OnClickListener {

    @Inject lateinit var newsViewModel: NewsViewModel

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.tv_toolbar_title) lateinit var tvTitle: TextView
    @BindView(R.id.rvArticles) lateinit var rvArticles: RecyclerView
    @BindView(R.id.pb_news_list) lateinit var pbLoading: ContentLoadingProgressBar
    @BindView(R.id.fab_filters_list) lateinit var fabFilter: FloatingActionButton
    @BindView(R.id.root_filter) lateinit var rootFilter: FrameLayout
    @BindView(R.id.btn_country) lateinit var btnCountry: LinearLayout
    @BindView(R.id.rvFilter) lateinit var rvFilter: RecyclerView
    @BindView(R.id.frame_filter_buttons) lateinit var frameFilterButtons: LinearLayout

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: NewsListAdapter

    private lateinit var layoutManagerHorizontal: LinearLayoutManager
    private lateinit var countryAdapter: CountryListAdapter

    fun start(activityContext: Activity) {
        val starter = Intent(activityContext, NewsActivity::class.java)
        activityContext.startActivity(starter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        initViews()
        initList()
        initFilterList()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_news
    }

    private fun initViews() {
        setTitle(newsViewModel.providePageTitle())
        setLoadingState()
        setListeners()
    }

    private fun setListeners(){
        fabFilter.setOnClickListener(this)
        btnCountry.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            fabFilter -> switchFilterLayout()
            btnCountry -> loadCountryFilterList()
        }
    }

    private fun initList() {
        layoutManager = GridLayoutManager(this,this.resources.getInteger(R.integer.span_grid_article))
        rvArticles.layoutManager = layoutManager

        adapter = NewsListAdapter(this)
        rvArticles.adapter = adapter

        adapter.getPositionClicks().subscribe{t ->
            NewsDetailActivity.start(this,t.first,t.second)
        }

        fetchArticles()
        setFabAnimation()
    }

    private fun setTitle(title: String){
        tvTitle.text = title
    }

    private fun setLoadingState(){
        addDisposable(newsViewModel.isLoadingObservable().observeOn(AndroidSchedulers.mainThread()).subscribe(this::showHideLoadingBar))
    }

    private fun showHideLoadingBar(showLoadingBar: Boolean){
        when(showLoadingBar){
            true -> pbLoading.show()
            false -> pbLoading.hide()
        }
    }

    private fun fetchArticles(){
        addDisposable(newsViewModel.getNewsArticles().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::loadNewsList))
    }

    private fun loadNewsList(headlineResult: HeadlineResult?){
        headlineResult?.let {
            adapter.addItems(headlineResult.articles)
        }
    }

    private fun setFabAnimation(){
        rvArticles.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && fabFilter.isShown)
                    fabFilter.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabFilter.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun switchFilterLayout(){
        when(rootFilter.visibility){
            View.VISIBLE -> rootFilter.visibility = View.GONE
            View.GONE -> rootFilter.visibility = View.VISIBLE
        }
    }

    private fun initFilterList(){
        layoutManagerHorizontal = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvFilter.layoutManager = layoutManagerHorizontal
    }

    private fun loadCountryFilterList(){

        when(frameFilterButtons.visibility){
            View.VISIBLE -> {
                frameFilterButtons.visibility = View.GONE
                rvFilter.visibility = View.VISIBLE
            }
        }
        layoutManagerHorizontal = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvFilter.layoutManager = layoutManagerHorizontal

        countryAdapter = CountryListAdapter(this)
        rvFilter.adapter = countryAdapter

        val countryMap: Map<String, String>? = GlobalFunctions.getHashMapResource(this,R.xml.country)
        val countries: ArrayList<Country> = ArrayList()

        countryMap?.let {
            for (item in countryMap.entries){
                var country = Country("",R.drawable.ic_uk)
                country.name = item.key
                country.drawableRes = GlobalFunctions.getDrawableResId(this,item.value)

                countries.add(country)
            }
        }

        countryAdapter.addItems(countries)
    }
}
