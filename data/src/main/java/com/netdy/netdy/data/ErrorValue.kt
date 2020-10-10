package com.netdy.netdy.data

sealed class ErrorValue(
    open val tag: String,
    open val technicalMessage: String
) : Error() {

    data class CurrentUserNotFound(
        override val tag: String,
        override val technicalMessage: String
    ) : ErrorValue(tag, technicalMessage)

    data class CanNotSignIn(
        override val tag: String,
        override val technicalMessage: String,
        val exception: Exception
    ) : ErrorValue(tag, technicalMessage)

    data class CanNotSignUp(
        override val tag: String,
        override val technicalMessage: String,
        val exception: Exception
    ) : ErrorValue(tag, technicalMessage)

    data class CanNotSignOut(
        override val tag: String,
        override val technicalMessage: String,
        val exception: Exception
    ) : ErrorValue(tag, technicalMessage)

    data class CanNotGetMessage(
        override val tag: String,
        override val technicalMessage: String,
        val exception: Exception
    ) : ErrorValue(tag, technicalMessage)
}