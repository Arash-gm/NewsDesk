package com.lovoo.newsdesk.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.ContentLoadingProgressBar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.TextView
import butterknife.BindView
import com.lovoo.newsdesk.R
import com.lovoo.newsdesk.base.BaseActivity
import com.lovoo.newsdesk.data.model.HeadlineResult
import com.lovoo.newsdesk.newsdetail.NewsDetailActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class NewsActivity : BaseActivity() {

    @Inject lateinit var newsViewModel: NewsViewModel

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.tv_toolbar_title) lateinit var tvTitle: TextView
    @BindView(R.id.rvArticles) lateinit var rvArticles: RecyclerView
    @BindView(R.id.pb_news_list) lateinit var pbLoading: ContentLoadingProgressBar

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: NewsListAdapter

    fun start(activityContext: Activity) {
        val starter = Intent(activityContext, NewsActivity::class.java)
        activityContext.startActivity(starter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        initViews()
        initList()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_news
    }

    private fun initViews() {
        setTitle(newsViewModel.providePageTitle())
        setLoadingState()
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
}
