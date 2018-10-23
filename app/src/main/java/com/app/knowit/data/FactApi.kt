package com.app.knowit.data

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Two layers of abstraction for [FactApi] implementation
 * [FactRetrofitApi] is a Retrofit interface of api
 * [FactApi] is the implementation layer of the [FactRetrofitApi]
 */

interface FactRetrofitApi {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    fun getFacts(): Single<Fact>
}

class FactApi(private val factRetrofitApi: FactRetrofitApi) {
    fun getFacts(): Single<Fact> {
        return factRetrofitApi.getFacts()
    }
}