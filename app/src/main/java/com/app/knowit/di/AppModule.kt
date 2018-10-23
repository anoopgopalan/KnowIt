package com.app.knowit.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides the Context
 */
@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}