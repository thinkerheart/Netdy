package com.netdy.netdy.domain.usecase

import org.koin.dsl.module

val usecaseModule = module {

    single { InitializeCommunicationUseCase(get()) }
    single { GetCurrentUserAccountSyncUseCase(get()) }
    single { SignInUseCase(get(), get()) }
    single { SignUpUseCase(get(), get()) }
    single { SignOutUseCase(get(), get()) }
    single { GetMessageUseCase(get(), get()) }
}