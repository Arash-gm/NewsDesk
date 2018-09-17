package com.lovoo.newsdesk.di.component

import com.lovoo.newsdesk.base.ApplicationClass
import com.lovoo.newsdesk.di.module.ApplicationModule
import com.lovoo.newsdesk.di.module.NetworkModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Arash on 9/14/2018.
 */

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class), (ApplicationModule::class), (NetworkModule::class)])
interface ApplicationComponent: AndroidInjector<ApplicationClass>{

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ApplicationClass>()
}