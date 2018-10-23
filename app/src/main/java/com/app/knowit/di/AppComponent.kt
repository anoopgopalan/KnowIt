package com.app.knowit.di

import com.app.knowit.KnowIt
import dagger.Component
import javax.inject.Singleton

/**
 * AppComponent builds all the dependencies and provides to the injected classes
 */
@Singleton
@Component(modules = [AppModule::class, OkHttpModule::class, RetrofitModule::class, FactModule::class])
interface AppComponent {
    fun inject(knowIt: KnowIt)
}