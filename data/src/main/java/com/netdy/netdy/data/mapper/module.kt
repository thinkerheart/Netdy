package com.netdy.netdy.data.mapper

import org.koin.dsl.module

val mapperModule = module {
    single { ParseUserMapper() }
    single { MessageParseObjectMapper() }
}