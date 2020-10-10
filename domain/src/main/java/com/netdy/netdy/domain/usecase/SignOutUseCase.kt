package com.netdy.netdy.domain.usecase

import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.domain.repository.UserAccountRepository
import com.netdy.netdy.domain.usecase.base.CompletableUseCase
import io.reactivex.Completable

class SignOutUseCase(
    private val userAccountRepository: UserAccountRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Unit>(postExecutionThread) {

    override fun buildCompletableUseCase(param: Unit): Completable {
        return userAccountRepository.signOut()
    }
}