package com.lovoo.newsdesk.di.module

import com.lovoo.newsdesk.data.respository.api.ApiCall
import com.lovoo.newsdesk.util.Globals
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Arash on 9/14/2018.
 */
@Module(includes = arrayOf(AndroidInjectionModule::class))
class NetworkModule{

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
                .baseUrl(Globals.API_END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideHttpClient())
                .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiCall {
        return retrofit.create<ApiCall>(ApiCall::class.java!!)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

}