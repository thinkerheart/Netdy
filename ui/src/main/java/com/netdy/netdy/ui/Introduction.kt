package com.netdy.netdy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.netdy.netdy.domain.entity.UserAccount
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.domain.usecase.GetCurrentUserAccountSyncUseCase
import com.netdy.netdy.domain.usecase.base.SynchronousUseCase
import com.netdy.netdy.ui.base.BaseFragment
import com.netdy.netdy.ui.base.MvRxViewModel
import com.netdy.netdy.ui.common.HiddenBottomBar
import com.netdy.netdy.ui.mapper.UserAccountUIModelMapper
import com.netdy.netdy.ui.model.UserAccountUIModel
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class IntroductionState(
    val currentUserAccount: UserAccountUIModel? = null
) : MvRxState

class IntroductionViewModel(
    initialState: IntroductionState,
    navigator: ScreenNavigator,
    private val getCurrentUserAccountSyncUseCase: GetCurrentUserAccountSyncUseCase,
    private val userAccountUIModelMapper: UserAccountUIModelMapper
) : MvRxViewModel<IntroductionState>(initialState, navigator) {

    init {
        getCurrentUserAccount()
    }

    private fun getCurrentUserAccount() {
        getCurrentUserAccountSyncUseCase.execute(
            Unit,
            object : SynchronousUseCase.Callback<UserAccount> {
                override fun onSuccess(result: UserAccount) {
                    handleCurrentUser(result)
                }

                override fun onError(throwable: Throwable) {
                    handleError(throwable)
                }
            }
        )
    }

    private fun handleCurrentUser(userAccount: UserAccount) {
        setState {
            copy(
                currentUserAccount = userAccountUIModelMapper.transform(userAccount)
            )
        }
    }

    companion object : MvRxViewModelFactory<IntroductionViewModel, IntroductionState>,
        KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: IntroductionState
        ): IntroductionViewModel? {
            return IntroductionViewModel(state, getKoin().get(), getKoin().get(), getKoin().get())
        }
    }
}

class IntroductionFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: IntroductionViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_introduction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectSubscribe(IntroductionState::currentUserAccount) {
            withState(viewModel) {
                if (it.currentUserAccount != null) navigator.displayNewsFeedAsNavRoot()
                else navigator.displayGettingStartedAsNavRoot()
            }
        }
    }

    override fun invalidate() {}
}