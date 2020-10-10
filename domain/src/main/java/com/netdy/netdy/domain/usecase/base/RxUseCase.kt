package com.netdy.netdy.domain.usecase.base

import com.netdy.netdy.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class AsynchronousRxUseCase(
    val postExecutionThread: PostExecutionThread
) {
    private val compositeDisposables: CompositeDisposable = CompositeDisposable()

    fun dispose() {
        if (!compositeDisposables.isDisposed)
            compositeDisposables.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        compositeDisposables.add(disposable)
    }
}

abstract class ObservableUseCase<Rs, Ps>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildObservableUseCase(param: Ps): Observable<Rs>

    fun execute(observer: DisposableObserver<Rs>, param: Ps) {
        val observable = buildObservableUseCase(param)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(observable.subscribeWith(observer))
    }
}

abstract class CompletableUseCase<Ps>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildCompletableUseCase(param: Ps): Completable

    fun execute(observer: DisposableCompletableObserver, param: Ps) {
        val completable = buildCompletableUseCase(param)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(completable.subscribeWith(observer))
    }
}

abstract class SingleUseCase<Rs, Ps>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildSingleUseCase(param: Ps): Single<Rs>

    fun execute(observer: DisposableSingleObserver<Rs>, param: Ps) {
        val single = buildSingleUseCase(param)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(single.subscribeWith(observer))
    }
}

abstract class MaybeUseCase<Rs, Ps>(
    postExecutionThread: PostExecutionThread
) : AsynchronousRxUseCase(postExecutionThread) {

    abstract fun buildMaybeUseCase(param: Ps): Maybe<Rs>

    fun execute(observer: DisposableMaybeObserver<Rs>, param: Ps) {
        val maybe = buildMaybeUseCase(param)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.getScheduler())
        addDisposable(maybe.subscribeWith(observer))
    }
}