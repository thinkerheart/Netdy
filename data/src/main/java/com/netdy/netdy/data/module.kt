package com.netdy.netdy.data

import com.netdy.netdy.data.mapper.mapperModule
import com.netdy.netdy.data.repository.repositoryModule

const val TAG = "com.netdy.netdy.data"

val dataModule = listOf(
    mapperModule,
    repositoryModule
)