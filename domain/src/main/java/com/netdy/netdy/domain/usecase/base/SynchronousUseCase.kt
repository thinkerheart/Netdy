package com.netdy.netdy.domain.usecase.base

interface SynchronousUseCase<Rs, Ps> {

    interface Callback<Rs> {
        fun onSuccess(result: Rs)
        fun onError(throwable: Throwable)
    }

    fun execute(param: Ps, callback:Callback<Rs>)
}