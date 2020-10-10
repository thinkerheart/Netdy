package com.netdy.netdy.domain.usecase.base

import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver

open class ObservableObserver<T> : DisposableObserver<T>() {
    override fun onComplete() {}
    override fun onNext(t: T) {}
    override fun onError(e: Throwable) {}
}

open class CompletableObserver : DisposableCompletableObserver() {
    override fun onComplete() {}
    override fun onError(e: Throwable) {}
}

open class SingleObserver<T> : DisposableSingleObserver<T>() {
    override fun onSuccess(t: T) {}
    override fun onError(e: Throwable) {}
}

open class MaybeObserver<T> : DisposableMaybeObserver<T>() {
    override fun onSuccess(t: T) {}
    override fun onComplete() {}
    override fun onError(e: Throwable) {}
}