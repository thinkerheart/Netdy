package com.netdy.netdy

import androidx.multidex.MultiDexApplication
import com.netdy.netdy.data.dataModule
import com.netdy.netdy.domain.domainModule
import com.netdy.netdy.domain.usecase.InitializeCommunicationUseCase
import com.netdy.netdy.domain.usecase.base.SynchronousUseCase
import com.netdy.netdy.ui.uiModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.PrintLogger

class NetdyAndroidApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            logger(PrintLogger(Level.DEBUG))
            androidContext(this@NetdyAndroidApplication)
            modules(dataModule)
            modules(domainModule)
            modules(uiModule)
        }

        val initializeCommunicationUseCase: InitializeCommunicationUseCase by inject()
        initializeCommunicationUseCase.execute(Unit, object : SynchronousUseCase.Callback<Unit> {

            override fun onSuccess(result: Unit) {

            }

            override fun onError(throwable: Throwable) {

            }
        })
    }
}