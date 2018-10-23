package com.app.knowit.ui.viewmodel

import android.arch.lifecycle.ViewModel
import com.app.knowit.R
import com.app.knowit.data.Fact
import com.app.knowit.data.FactRepository
import com.app.knowit.data.Info
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 * ViewModel which helps to communicate with data layer are get the responses
 */

class FactViewModel(private val factRepository: FactRepository) : ViewModel() {

    private val factLoadsSubject = PublishSubject.create<List<Info>>()
    val factLoads: Observable<List<Info>> = factLoadsSubject.hide()

    private val titleUpdatesSubject = PublishSubject.create<String>()
    val titleUpdates: Observable<String> = titleUpdatesSubject.hide()

    private val errorMessageShowsSubject = PublishSubject.create<Int>()
    val errorMessageShows: Observable<Int> = errorMessageShowsSubject.hide()

    private val progressBarVisibilityChangesSubject = PublishSubject.create<Boolean>()
    val progressBarVisibilityChanges: Observable<Boolean> = progressBarVisibilityChangesSubject.hide()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var fact: Fact? = null

    fun loadFacts() {
        progressBarVisibilityChangesSubject.onNext(true)
        if (fact != null) {
            factLoadsSubject.onNext(fact?.rows!!)
            progressBarVisibilityChangesSubject.onNext(false)
        } else {
            getFactsFromApi()
        }
    }

    private fun getFactsFromApi() {
        compositeDisposable.add(factRepository.getFacts()
            .doAfterTerminate { progressBarVisibilityChangesSubject.onNext(false) }
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->
                Timber.d("Api response", it)
                if (it?.rows != null) {
                    factLoadsSubject.onNext(it.rows.filter { info -> (info.title != null || info.description != null || info.imageHref != null) })
                    fact = it
                } else {
                    errorMessageShowsSubject.onNext(R.string.something_went_wrong_try_again_later)
                }
                it?.title?.let {
                    titleUpdatesSubject.onNext(it)
                }

            }, {
                errorMessageShowsSubject.onNext(R.string.something_went_wrong_try_again_later)
                Timber.e("Error", it)
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun swipedToRefreshData() {
        progressBarVisibilityChangesSubject.onNext(true)
        getFactsFromApi()
    }

}