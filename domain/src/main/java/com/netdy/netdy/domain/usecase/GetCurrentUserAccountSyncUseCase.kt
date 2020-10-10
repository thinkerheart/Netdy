package com.netdy.netdy.domain.usecase

import com.netdy.netdy.domain.entity.UserAccount
import com.netdy.netdy.domain.repository.UserAccountRepository
import com.netdy.netdy.domain.usecase.base.SynchronousUseCase

class GetCurrentUserAccountSyncUseCase(
    private val userAccountRepository: UserAccountRepository
) : SynchronousUseCase<UserAccount, Unit> {

    override fun execute(param: Unit, callback: SynchronousUseCase.Callback<UserAccount>) {
        userAccountRepository.getCurrentUserAccountSync(callback)
    }
}