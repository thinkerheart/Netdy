package com.netdy.netdy.data.repository

import com.netdy.netdy.domain.repository.CommunicationRepository
import com.netdy.netdy.domain.repository.MessageRepository
import com.netdy.netdy.domain.repository.UserAccountRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<CommunicationRepository> { CommunicationRepository(get()) }
    single<UserAccountRepository> { UserAccountRepository(get(), get()) }
    single<MessageRepository> { MessageRepository(get(), get()) }
}