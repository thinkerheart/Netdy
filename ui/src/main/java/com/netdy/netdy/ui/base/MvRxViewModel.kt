package com.netdy.netdy.ui.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.BaseMvRxViewModel
import com.airbnb.mvrx.MvRxState
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.ui.TAG_NETDY_DEBUG

abstract class MvRxViewModel<S: MvRxState>(
    initialState: S,
    val navigator: ScreenNavigator
) : BaseMvRxViewModel<S>(initialState, debugMode = true) {

    val errorsMutableLiveData: MutableLiveData<Throwable> = MutableLiveData()

    init {
        Log.d(TAG_NETDY_DEBUG, "Initializing: $this")
        logStateChanges()
    }

    protected fun handleError(throwable: Throwable) {
        errorsMutableLiveData.value = throwable
    }
}