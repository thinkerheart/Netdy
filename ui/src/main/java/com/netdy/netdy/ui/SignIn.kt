package com.netdy.netdy.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.mvrx.*
import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.domain.usecase.SignInUseCase
import com.netdy.netdy.ui.base.BaseFragment
import com.netdy.netdy.ui.base.MvRxViewModel
import com.netdy.netdy.ui.common.HiddenBottomBar
import com.netdy.netdy.ui.mapper.UserAccountUIModelMapper
import com.netdy.netdy.ui.model.UserAccountUIModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_in.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class SignInState(
    val userName: String = "",
    val password: String = "",
    val signedInUserAccountUIModel: Async<UserAccountUIModel> = Uninitialized
) : MvRxState

class SignInViewModel(
    initialState: SignInState,
    navigator: ScreenNavigator,
    private val userAccountUIModelMapper: UserAccountUIModelMapper,
    private val signInUseCase: SignInUseCase,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<SignInState>(initialState, navigator) {

    fun signIn() {
        withState { state ->
            signInUseCase.buildSingleUseCase(SignInUseCase.Ps(state.userName, state.password))
                .subscribeOn(Schedulers.io())
                .observeOn(uiThread.getScheduler())
                .execute({ userAccountUIModelMapper.transform(it) }) {
                    copy(signedInUserAccountUIModel = it)
                }
        }
    }

    fun setUserNameState(userName: String) {
        setState {
            copy(
                userName = userName
            )
        }
    }

    fun setPasswordState(password: String) {
        setState {
            copy(
                password = password
            )
        }
    }

    companion object : MvRxViewModelFactory<SignInViewModel, SignInState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: SignInState
        ): SignInViewModel? {
            return SignInViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get(),
                getKoin().get()
            )
        }
    }
}

class SignInFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: SignInViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edtUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { viewModel.setUserNameState(it.toString()) }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { viewModel.setPasswordState(it.toString()) }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnSignIn.setOnClickListener { viewModel.signIn() }

        viewModel.selectSubscribe(SignInState::signedInUserAccountUIModel) {
            withState(viewModel) {
                if (it.signedInUserAccountUIModel is Success) navigator.displayNewsFeedAsNavRoot()
            }
        }
    }

    override fun invalidate() {}
}