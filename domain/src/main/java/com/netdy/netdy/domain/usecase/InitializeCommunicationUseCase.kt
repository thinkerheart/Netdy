package com.netdy.netdy.domain.usecase

import com.netdy.netdy.domain.repository.CommunicationRepository
import com.netdy.netdy.domain.usecase.base.SynchronousUseCase

class InitializeCommunicationUseCase(
    private val communicationRepository: CommunicationRepository
) : SynchronousUseCase<Unit, Unit> {

    override fun execute(param: Unit, callback: SynchronousUseCase.Callback<Unit>) {
        try {
            communicationRepository.initializeCommunication()
            callback.onSuccess(Unit)
        } catch (ex: Exception) {
            callback.onError(ex)
        }
    }
}