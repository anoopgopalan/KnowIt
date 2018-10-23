package com.app.knowit.data

import io.reactivex.Single

/**
 * This repository helps to fetch the facts from the [FactApi] and provides the results to viewModels
 */

class FactRepository(private val factApi: FactApi) {

    fun getFacts(): Single<Fact> {
        return factApi.getFacts()
    }
}