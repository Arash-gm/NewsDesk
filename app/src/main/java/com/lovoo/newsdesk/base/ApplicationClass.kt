package com.lovoo.newsdesk.base

import android.app.Activity
import android.app.Application
import com.lovoo.newsdesk.di.component.ApplicationComponent
import com.lovoo.newsdesk.di.component.DaggerApplicationComponent
import com.orhanobut.hawk.Hawk
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Arash on 9/14/2018.
 */
class ApplicationClass: Application(),HasActivityInjector{

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().create(this) as ApplicationComponent
        applicationComponent.inject(this)
        Hawk.init(this).build()
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    fun getComponent(): ApplicationComponent {
        return applicationComponent
    }

}