package com.netdy.netdy.domain.repository

import com.netdy.netdy.domain.entity.Message
import io.reactivex.Observable

interface MessageRepository {

    fun getMessage(
        withUserId: String,
        numberOfMessage: Int,
        numberOfSkippedMessage: Int
    ): Observable<List<Message>>
}