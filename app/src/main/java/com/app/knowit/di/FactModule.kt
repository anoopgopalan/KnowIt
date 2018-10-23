package com.app.knowit.di

import com.app.knowit.data.FactApi
import com.app.knowit.data.FactRepository
import com.app.knowit.data.FactRetrofitApi
import com.app.knowit.ui.viewmodel.FactViewModelProvideFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Provides the Fact Api dependencies
 */

@Module
class FactModule {

    @Provides
    @Singleton
    fun providesFactRetrofitApi(retrofit: Retrofit): FactRetrofitApi {
        return retrofit.create(FactRetrofitApi::class.java)
    }

    @Provides
    @Singleton
    fun providesFactApi(factRetrofitApi: FactRetrofitApi): FactApi {
        return FactApi(factRetrofitApi)
    }

    @Provides
    @Singleton
    fun providesFActRepository(factApi: FactApi): FactRepository {
        return FactRepository(factApi)
    }

    @Provides
    @Singleton
    fun providesFactViewModelProviderFactory(factRepository: FactRepository): FactViewModelProvideFactory {
        return FactViewModelProvideFactory(factRepository)
    }

}