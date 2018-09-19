package com.lovoo.newsdesk.news

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.lovoo.newsdesk.R
import com.lovoo.newsdesk.data.model.Article
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by Arash on 9/15/2018.
 */
class NewsListAdapter(val context: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: ArrayList<Article> = ArrayList()
    private val onClickSubject = PublishSubject.create<Pair<Article,ImageView>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_list_row, parent, false)
        val holder = ItemViewHolder(view)
        view.setOnClickListener {
            val article = mList[holder.adapterPosition]
            var pair= Pair(article,holder.imgArticle)
            onClickSubject.onNext(pair)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).tvTitle.text = mList[position].title
        var requestOption: RequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_no_image)
        Glide.with(context).load(mList[position].imageUrl).apply(requestOption).into(holder.imgArticle)

    }

    override fun getItemCount(): Int {
        return this.mList.size
    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_news_list_title)
        val imgArticle: ImageView = view.findViewById(R.id.img_news_list_image)
    }

    fun addItems(articles: ArrayList<Article>) {
        mList.clear()
        mList.addAll(articles)
        notifyDataSetChanged()
    }


    fun getPositionClicks(): Observable<Pair<Article, ImageView>> {
        return onClickSubject
    }
}