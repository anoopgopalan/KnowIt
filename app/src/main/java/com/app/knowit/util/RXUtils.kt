package com.app.knowit.util

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

fun <T> Observable<T>.subscribeAndObserveOnMainThread(onNext: (t: T) -> Unit): Disposable {
    return observeOn(AndroidSchedulers.mainThread())
            .subscribe(onNext)
}
