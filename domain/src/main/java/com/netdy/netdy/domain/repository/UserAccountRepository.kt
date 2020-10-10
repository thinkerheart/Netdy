package com.netdy.netdy.domain.repository

import com.netdy.netdy.domain.entity.UserAccount
import com.netdy.netdy.domain.usecase.base.SynchronousUseCase
import io.reactivex.Completable
import io.reactivex.Single

interface UserAccountRepository {

    fun getCurrentUserAccountSync(callback: SynchronousUseCase.Callback<UserAccount>)

    fun signIn(userName: String, password: String): Single<UserAccount>

    fun signUp(
        userName: String,
        password: String,
        firstName: String,
        lastName: String
    ): Single<UserAccount>

    fun signOut(): Completable
}