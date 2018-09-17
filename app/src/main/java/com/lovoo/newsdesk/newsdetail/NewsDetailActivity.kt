package com.lovoo.newsdesk.newsdetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lovoo.newsdesk.R
import com.lovoo.newsdesk.base.BaseActivity
import com.lovoo.newsdesk.data.model.Article
import com.lovoo.newsdesk.util.Globals
import javax.inject.Inject



class NewsDetailActivity : BaseActivity() {

    @Inject lateinit var newsDetailViewModel: NewsDetailViewModel

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.tv_toolbar_title) lateinit var tvTitle: TextView
    @BindView(R.id.img_news_detail) lateinit var imgMain: ImageView
    @BindView(R.id.tv_news_detail_headline) lateinit var tvHeadline: TextView
    @BindView(R.id.tv_news_detail_desc) lateinit var tvDesc: TextView

    companion object {
        fun start(activityContext: Activity,article: Article,sharedImageView: ImageView) {
            val starter = Intent(activityContext, NewsDetailActivity::class.java)
            starter.putExtra(Globals.KEY_BUNDLE_ARTICLE, article)

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activityContext,
                    sharedImageView,
                    ViewCompat.getTransitionName(sharedImageView))

            activityContext.startActivity(starter,options.toBundle())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        getinitialBundle(intent)
        initViews()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_news_detail
    }

    private fun initViews() {
        addDisposable(newsDetailViewModel.providePageTitle().subscribe(::setTitle))
        addDisposable(newsDetailViewModel.provideMainImage().subscribe(::loadMainImage))
        addDisposable(newsDetailViewModel.provideHeadline().subscribe(::setHeadline))
        addDisposable(newsDetailViewModel.provideDesc().subscribe(::setDesc))
    }

    private fun getinitialBundle(intent: Intent?) {
        intent?.let {
            val article = intent.getParcelableExtra<Article>(Globals.KEY_BUNDLE_ARTICLE) as Article
            article?.let {
                newsDetailViewModel.setLoadedArticle(article)
            }
        }
    }

    private fun setTitle(title: String?){
        tvTitle.text = title
    }

    private fun loadMainImage(imageUrl: String){
        var requestOption: RequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_no_image)
        Glide.with(this).load(imageUrl).apply(requestOption).into(imgMain)
    }

    private fun setHeadline(headline: String?){
        tvHeadline.text = headline
    }

    private fun setDesc(desc: String?){
        tvDesc.text = desc
    }
}