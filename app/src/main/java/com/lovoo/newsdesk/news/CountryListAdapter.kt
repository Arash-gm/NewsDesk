package com.lovoo.newsdesk.news

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lovoo.newsdesk.R
import com.lovoo.newsdesk.data.model.Country
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*


/**
 * Created by Arash on 9/15/2018.
 */
class CountryListAdapter(val context: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: ArrayList<Country> = ArrayList()
    private val onClickSubject = PublishSubject.create<Country>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_list_row, parent, false)
        val holder = ItemViewHolder(view)
        view.setOnClickListener {
            val country = mList[holder.adapterPosition]
            onClickSubject.onNext(country)
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(context).load(mList[position].drawableRes).into((holder as ItemViewHolder).imgCountry)

    }

    override fun getItemCount(): Int {
        return this.mList.size
    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgCountry: ImageView = view.findViewById(R.id.img_country_list_row)
    }

    fun addItems(countries: ArrayList<Country>) {
        mList.addAll(countries)
        notifyDataSetChanged()
    }


    fun getPositionClicks(): Observable<Country> {
        return onClickSubject
    }
}