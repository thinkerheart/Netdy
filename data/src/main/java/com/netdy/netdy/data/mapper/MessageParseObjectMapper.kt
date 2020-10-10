package com.netdy.netdy.data.mapper

import com.netdy.netdy.domain.entity.Message
import com.parse.ParseObject

class MessageParseObjectMapper {

    fun transform(messageParseObject: ParseObject): Message {

        return Message(
            id = messageParseObject.objectId,
            sender = messageParseObject.getString("sender") ?: "",
            receiver = messageParseObject.getString("receiver") ?: "",
            content = messageParseObject.getString("content") ?: ""
        )
    }

    fun transforms(messageParseObjects: List<ParseObject>): List<Message> {

        val messages: MutableList<Message> = mutableListOf()

        messageParseObjects.forEach { messages.add(transform(it)) }

        return messages.toList()
    }
}