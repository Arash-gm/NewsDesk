package com.lovoo.newsdesk.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Arash on 9/14/2018.
 */
abstract class BaseActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())

        ButterKnife.bind(this)
    }

    protected abstract fun getLayoutResourceId(): Int

    private fun clearDisposable() {
        disposables.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onStop() {
        super.onStop()
        clearDisposable()
    }
}