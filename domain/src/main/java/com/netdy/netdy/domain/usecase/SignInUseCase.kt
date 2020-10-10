package com.netdy.netdy.domain.usecase

import com.netdy.netdy.domain.entity.UserAccount
import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.domain.repository.UserAccountRepository
import com.netdy.netdy.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class SignInUseCase(
    private val userAccountRepository: UserAccountRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<UserAccount, SignInUseCase.Ps>(postExecutionThread) {

    data class Ps(
        val userName: String = "",
        val password: String = ""
    )

    override fun buildSingleUseCase(param: Ps): Single<UserAccount> {
        return userAccountRepository.signIn(param.userName, param.password)
    }
}