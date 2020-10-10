package com.netdy.netdy.ui.mapper

import org.koin.dsl.module

val mapperModule = module {

    single { UserAccountUIModelMapper() }
    single { MessageUIModelMapper() }
}