package com.netdy.netdy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.airbnb.mvrx.fragmentViewModel
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.ui.base.BaseFragment
import com.netdy.netdy.ui.base.MvRxViewModel
import com.netdy.netdy.ui.common.HiddenBottomBar
import kotlinx.android.synthetic.main.fragment_gettingstarted.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class GettingStartedState(val dummy: String? = null) : MvRxState

class GettingStartedViewModel(
    initialState: GettingStartedState,
    navigator: ScreenNavigator
) : MvRxViewModel<GettingStartedState>(initialState, navigator) {

    companion object : MvRxViewModelFactory<GettingStartedViewModel, GettingStartedState>,
        KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: GettingStartedState
        ): GettingStartedViewModel? {
            return GettingStartedViewModel(state, getKoin().get())
        }
    }
}

class GettingStartedFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: GettingStartedViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gettingstarted, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignUp.setOnClickListener { navigator.navigateToSignUp() }
        btnSignIn.setOnClickListener { navigator.navigateToSignIn() }
    }

    override fun invalidate() {}
}