package com.netdy.netdy.domain.usecase

import com.netdy.netdy.domain.entity.Message
import com.netdy.netdy.domain.executor.PostExecutionThread
import com.netdy.netdy.domain.repository.MessageRepository
import com.netdy.netdy.domain.usecase.base.ObservableUseCase
import io.reactivex.Observable

class GetMessageUseCase(
    private val messageRepository: MessageRepository,
    postExecutionThread: PostExecutionThread
) : ObservableUseCase<List<Message>, GetMessageUseCase.Ps>(postExecutionThread) {

    override fun buildObservableUseCase(param: Ps): Observable<List<Message>> {
        return messageRepository.getMessage(
            param.withUserId, param.numberOfMessage, param.numberOfSkippedMessage
        )
    }

    data class Ps(
        val withUserId: String = "",
        val numberOfMessage: Int = 0,
        val numberOfSkippedMessage: Int = 0
    )
}