package com.netdy.netdy.data.repository

import android.content.Context
import com.netdy.netdy.data.ErrorValue
import com.netdy.netdy.data.R
import com.netdy.netdy.data.TAG
import com.netdy.netdy.data.mapper.MessageParseObjectMapper
import com.netdy.netdy.domain.entity.Message
import com.netdy.netdy.domain.repository.MessageRepository
import com.parse.ParseObject
import com.parse.ParseQuery
import com.parse.ParseUser
import io.reactivex.Observable

class MessageRepository(
    private val context: Context,
    private val messageParseObjectMapper: MessageParseObjectMapper
) : MessageRepository {

    override fun getMessage(
        withUserId: String,
        numberOfMessage: Int,
        numberOfSkippedMessage: Int
    ): Observable<List<Message>> {

        return Observable.create { emitter ->

            val messagesWithUserIdQuery: ParseQuery<ParseObject> = ParseQuery.getQuery(
                "Message"
            )

            messagesWithUserIdQuery.orderByDescending("createdAt")
            messagesWithUserIdQuery.limit = numberOfMessage
            messagesWithUserIdQuery.skip = numberOfSkippedMessage
            messagesWithUserIdQuery.whereContainedIn(
                "sender",
                listOf(ParseUser.getCurrentUser().objectId, withUserId)
            )
            messagesWithUserIdQuery.whereContainedIn(
                "receiver",
                listOf(ParseUser.getCurrentUser().objectId, withUserId)
            )

            messagesWithUserIdQuery.fromPin("MessagesWithMyHeart")

            messagesWithUserIdQuery.findInBackground {
                    messagesWithUserId, messagesWithUserIdException ->

                if (messagesWithUserIdException == null && messagesWithUserId.isNotEmpty()) {
                    emitter.onNext(
                        messageParseObjectMapper.transforms(messagesWithUserId.reversed())
                    )
                    emitter.onComplete()
                } else {
                    messagesWithUserIdQuery.fromNetwork()

                    messagesWithUserIdQuery.findInBackground {
                            messagesWithUserIdFromNet, messagesWithUserIdFromNetException ->

                        if (messagesWithUserIdFromNetException != null) {
                            if (messagesWithUserIdException == null &&
                                messagesWithUserId.isEmpty()
                            ) {

                                emitter.onNext(
                                    messageParseObjectMapper.transforms(
                                        messagesWithUserId.reversed()
                                    )
                                )
                                emitter.onComplete()
                            } else {
                                emitter.onError(
                                    ErrorValue.CanNotGetMessage(
                                        TAG,
                                        context.getString(R.string.can_not_get_message),
                                        messagesWithUserIdFromNetException
                                    )
                                )
                            }
                        } else {

                            ParseObject.pinAllInBackground(
                                "MessagesWithMyHeart", messagesWithUserIdFromNet
                            ) { pinMessagesWithMyHeartException ->

                                if (pinMessagesWithMyHeartException == null) {
                                    emitter.onNext(
                                        messageParseObjectMapper.transforms(
                                            messagesWithUserIdFromNet.reversed()
                                        )
                                    )
                                    emitter.onComplete()
                                } else {
                                    if (messagesWithUserIdException == null &&
                                        messagesWithUserId.isEmpty()
                                    ) {

                                        emitter.onNext(
                                            messageParseObjectMapper.transforms(
                                                messagesWithUserId.reversed()
                                            )
                                        )
                                        emitter.onComplete()
                                    } else {
                                        emitter.onError(
                                            ErrorValue.CanNotGetMessage(
                                                TAG,
                                                context.getString(R.string.can_not_get_message),
                                                pinMessagesWithMyHeartException
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}