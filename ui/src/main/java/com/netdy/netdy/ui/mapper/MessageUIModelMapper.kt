package com.netdy.netdy.ui.mapper

import com.netdy.netdy.domain.entity.Message
import com.netdy.netdy.ui.model.MessageUIModel

class MessageUIModelMapper {

    fun transform(message: Message): MessageUIModel {

        return MessageUIModel(
            id = message.id,
            sender = message.sender,
            receiver = message.receiver,
            content = message.content
        )
    }

    fun transforms(messages: List<Message>): List<MessageUIModel> {

        val messageUIModels: MutableList<MessageUIModel> = mutableListOf()

        messages.forEach { messageUIModels.add(transform(it)) }

        return messageUIModels.toList()
    }
}