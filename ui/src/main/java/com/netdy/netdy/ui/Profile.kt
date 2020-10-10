package com.netdy.netdy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.*
import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.domain.usecase.SignOutUseCase
import com.netdy.netdy.ui.base.BaseFragment
import com.netdy.netdy.ui.base.MvRxViewModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class ProfileState(
    val isSignedOut: Async<Unit> = Uninitialized
) : MvRxState

class ProfileViewModel(
    initialState: ProfileState,
    navigator: ScreenNavigator,
    private val signOutUseCase: SignOutUseCase,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<ProfileState>(initialState, navigator) {

    fun signOut() {
        signOutUseCase.buildCompletableUseCase(Unit)
            .subscribeOn(Schedulers.io())
            .observeOn(uiThread.getScheduler())
            .execute {
                copy(isSignedOut = it)
            }
    }

    companion object : MvRxViewModelFactory<ProfileViewModel, ProfileState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: ProfileState
        ): ProfileViewModel? {
            return ProfileViewModel(state, getKoin().get(), getKoin().get(), getKoin().get())
        }
    }
}

class ProfileFragment : BaseFragment() {

    override val viewModel: ProfileViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignOut.setOnClickListener { viewModel.signOut() }

        viewModel.selectSubscribe(ProfileState::isSignedOut) {
            withState(viewModel) {
                if (it.isSignedOut is Success) {
                    navigator.resetMainScreen()
                }
            }
        }
    }

    override fun invalidate() {}
}