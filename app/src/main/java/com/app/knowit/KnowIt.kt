package com.app.knowit

import android.app.Application
import com.app.knowit.di.*
import com.app.knowit.util.BASE_URL

class KnowIt : Application() {

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var instance: KnowIt
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initAppComponent()
        appComponent.inject(this)
    }

    private fun initAppComponent() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(instance))
            .okHttpModule(OkHttpModule())
            .retrofitModule(RetrofitModule(BASE_URL))
            .factModule(FactModule())
            .build()
    }
}