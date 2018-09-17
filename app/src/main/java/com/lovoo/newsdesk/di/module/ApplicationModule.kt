package com.lovoo.newsdesk.di.module

import android.app.Application
import com.lovoo.newsdesk.base.ApplicationClass
import com.lovoo.newsdesk.news.NewsActivity
import com.lovoo.newsdesk.newsdetail.NewsDetailActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Created by Arash on 9/14/2018.
 */

@Module(includes = arrayOf(AndroidInjectionModule::class))
abstract class ApplicationModule {

    @ContributesAndroidInjector()
    abstract fun newsActivityInjector(): NewsActivity

    @ContributesAndroidInjector()
    abstract fun newsDetailActivityInjector(): NewsDetailActivity


    @Binds
    abstract fun application(applicationClass: ApplicationClass): Application
}