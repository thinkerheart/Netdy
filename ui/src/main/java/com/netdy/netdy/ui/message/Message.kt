package com.netdy.netdy.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.mvrx.*
import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.domain.navigation.ScreenNavigator
import com.netdy.netdy.domain.usecase.GetMessageUseCase
import com.netdy.netdy.ui.base.BaseEpoxyFragment
import com.netdy.netdy.ui.base.MvRxViewModel
import com.netdy.netdy.ui.mapper.MessageUIModelMapper
import com.netdy.netdy.ui.model.MessageUIModel
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import com.netdy.netdy.ui.R
import com.netdy.netdy.ui.base.MvRxEpoxyController
import kotlinx.android.synthetic.main.fragment_message.view.*

data class MessageState(
    val messages: Async<List<MessageUIModel>> = Uninitialized
) : MvRxState

class MessageViewModel(
    initialState: MessageState,
    navigator: ScreenNavigator,
    private val getMessageUseCase: GetMessageUseCase,
    private val messageUIModelMapper: MessageUIModelMapper,
    private val uiThread: PostExecutionThread
) : MvRxViewModel<MessageState>(initialState, navigator) {

    fun getMessages(withUserId: String, numberOfMessage: Int, numberOfSkippedMessage: Int) {

        withState(this) {

            val messages = it.messages()

            getMessageUseCase.buildObservableUseCase(
                GetMessageUseCase.Ps(withUserId, numberOfMessage, numberOfSkippedMessage)
            )
                .subscribeOn(Schedulers.io())
                .observeOn(uiThread.getScheduler())
                .execute( { messageUIModelMapper.transforms(it) } ) {
                    if (it !is Uninitialized && it !is Loading && it !is Fail) {
                        val messageList = mutableListOf<MessageUIModel>()
                        it.invoke()?.let { it1 -> messageList.addAll(it1) }
                        messages?.let { it1 -> messageList.addAll(it1) }
                        copy(messages = Success(messageList))
                    } else {
                        copy(messages = it)
                    }
                }
        }
    }

    companion object : MvRxViewModelFactory<MessageViewModel, MessageState>, KoinComponent {

        override fun create(
            viewModelContext: ViewModelContext,
            state: MessageState
        ): MessageViewModel? {
            return MessageViewModel(
                state,
                getKoin().get(),
                getKoin().get(),
                getKoin().get(),
                getKoin().get()
            )
        }
    }
}

class MessageFragment : BaseEpoxyFragment() {

    override val viewModel: MessageViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message, container, false).apply {
            recyclerView = messagesEpoxyRecycleView
            recyclerView.setController(epoxyController)

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(-1) &&
                        newState == RecyclerView.SCROLL_STATE_IDLE) {

                        Toast.makeText(context, "at top !!!", Toast.LENGTH_LONG).show()
                        withState(viewModel) {
                            if (it.messages !is Loading) {
                                viewModel.getMessages(
                                    "zM8cY6W5RD",
                                    30,
                                    it.messages()?.size ?: 0
                                )
                            }
                        }
                    }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()

        withState(viewModel) {
            viewModel.getMessages(
                "zM8cY6W5RD",
                30,
                it.messages()?.size ?: 0
            )
        }
    }

    override fun epoxyController() = simpleController(viewModel) { state ->

        state.messages()?.forEach {
            messageItem {
                id(it.id)
                if (it.sender == "zM8cY6W5RD") {
                    heartMessage(it.content)
                    information("")
                    myMessage("")
                } else if (it.receiver == "zM8cY6W5RD") {
                    myMessage(it.content)
                    information("")
                    heartMessage("")
                }
            }
        }
    }
}