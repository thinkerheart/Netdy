package com.netdy.netdy.domain.usecase

import com.netdy.netdy.domain.entity.UserAccount
import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.domain.repository.UserAccountRepository
import com.netdy.netdy.domain.usecase.base.SingleUseCase
import io.reactivex.Single

class SignUpUseCase(
    private val userAccountRepository: UserAccountRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<UserAccount, SignUpUseCase.Ps>(postExecutionThread) {

    data class Ps(
        val userName: String = "",
        val password: String = "",
        val firstName: String = "",
        val lastName: String = ""
    )

    override fun buildSingleUseCase(param: Ps): Single<UserAccount> {
        return userAccountRepository.signUp(
            param.userName,
            param.password,
            param.firstName,
            param.lastName
        )
    }
}