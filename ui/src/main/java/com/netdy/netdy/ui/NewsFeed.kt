package com.netdy.netdy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.airbnb.mvrx.fragmentViewModel
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.ui.base.BaseFragment
import com.netdy.netdy.ui.base.MvRxViewModel
import org.koin.core.KoinComponent

data class NewsFeedState(val dummy: String? = null) : MvRxState

class NewsFeedViewModel(
    initialState: NewsFeedState,
    navigator: ScreenNavigator
) : MvRxViewModel<NewsFeedState>(initialState, navigator) {

    companion object : MvRxViewModelFactory<NewsFeedViewModel, NewsFeedState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: NewsFeedState
        ): NewsFeedViewModel? {
            return NewsFeedViewModel(state, getKoin().get())
        }
    }
}

class NewsFeedFragment : BaseFragment() {

    override val viewModel: NewsFeedViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun invalidate() {}
}