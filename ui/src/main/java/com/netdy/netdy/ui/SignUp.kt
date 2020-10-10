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
import com.netdy.netdy.domain.usecase.SignUpUseCase
import com.netdy.netdy.ui.base.BaseFragment
import com.netdy.netdy.ui.base.MvRxViewModel
import com.netdy.netdy.ui.common.HiddenBottomBar
import com.netdy.netdy.ui.mapper.UserAccountUIModelMapper
import com.netdy.netdy.ui.model.UserAccountUIModel
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.edtPassword
import kotlinx.android.synthetic.main.fragment_sign_in.edtUserName
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent

data class SignUpState(
    val userName: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val signedUpUserAccountUIModel: Async<UserAccountUIModel> = Uninitialized
) : MvRxState

class SignUpViewModel(
    initialState: SignUpState,
    navigator: ScreenNavigator,
    private val signUpUseCase: SignUpUseCase,
    private val userAccountUIModelMapper: UserAccountUIModelMapper,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<SignUpState>(initialState, navigator) {

    fun signUp() {
        withState { state ->
            signUpUseCase.buildSingleUseCase(
                SignUpUseCase.Ps(state.userName, state.password, state.firstName, state.lastName)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(uiThread.getScheduler())
                .execute({ userAccountUIModelMapper.transform(it) }) {
                    copy(signedUpUserAccountUIModel = it)
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

    fun setFirstNameState(firstName: String) {
        setState {
            copy(
                firstName = firstName
            )
        }
    }

    fun setLastNameState(lastName: String) {
        setState {
            copy(
                lastName = lastName
            )
        }
    }

    companion object : MvRxViewModelFactory<SignUpViewModel, SignUpState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: SignUpState
        ): SignUpViewModel? {
            return SignUpViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get(),
                getKoin().get()
            )
        }
    }
}

class SignUpFragment : BaseFragment(), HiddenBottomBar {

    override val viewModel: SignUpViewModel by fragmentViewModel()
    private val navigator: ScreenNavigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
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

        edtFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { viewModel.setFirstNameState(it.toString()) }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        edtLastName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.let { viewModel.setLastNameState(it.toString()) }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnSignUp.setOnClickListener { viewModel.signUp() }

        viewModel.selectSubscribe(SignUpState::signedUpUserAccountUIModel) {
            withState(viewModel) {
                if (it.signedUpUserAccountUIModel is Success) navigator.displayNewsFeedAsNavRoot()
            }
        }
    }

    override fun invalidate() {}
}