package com.lovoo.newsdesk.news

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.lovoo.newsdesk.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by Arash on 9/15/2018.
 */
class CategoryListAdapter(val context: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: ArrayList<String> = ArrayList()
    private val onClickSubject = PublishSubject.create<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_list_row, parent, false)
        val holder = ItemViewHolder(view)
        view.setOnClickListener {
            val category = mList[holder.adapterPosition]
            onClickSubject.onNext(category)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).tvCategory.text = mList[position]

    }

    override fun getItemCount(): Int {
        return this.mList.size
    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategory: TextView = view.findViewById(R.id.tv_category_list_row)
    }

    fun addItems(categories: ArrayList<String>) {
        mList.clear()
        mList.addAll(categories)
        notifyDataSetChanged()
    }


    fun getPositionClicks(): Observable<String> {
        return onClickSubject
    }
}