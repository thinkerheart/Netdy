package com.netdy.netdy.ui.common

import com.netdy.netdy.domain.navigation.ScreenNavigator
import org.koin.dsl.bind
import org.koin.dsl.module

val commonModule = module {
    single { FragmentNavigator() } bind ScreenNavigator::class
}