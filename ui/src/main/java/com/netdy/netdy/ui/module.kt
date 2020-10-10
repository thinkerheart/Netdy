package com.netdy.netdy.ui

import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.ui.common.commonModule
import com.netdy.netdy.ui.mapper.mapperModule
import org.koin.dsl.module

const val TAG_NETDY_DEBUG = "nd.debug"

val uiModule = listOf(
    module {
        single<PostExecutionThread> { UIThread() }
    },
    commonModule,
    mapperModule
)