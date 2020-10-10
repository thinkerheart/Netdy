package com.netdy.netdy.ui.message

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.netdy.netdy.ui.R
import kotlinx.android.synthetic.main.item_message.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MessageItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val heartMessage: TextView
    private val information: TextView
    private val myMessage: TextView

    init {
        inflate(context, R.layout.item_message, this)
        heartMessage = txvHeartMessage
        information = txvInformation
        myMessage = txvMyMessage
    }

    @TextProp
    fun setHeartMessage(heartMessage: CharSequence) {
        this.heartMessage.text = heartMessage
    }

    @TextProp
    fun setInformation(information: CharSequence) {
        this.information.text = information
    }

    @TextProp
    fun setMyMessage(myMessage: CharSequence) {
        this.myMessage.text = myMessage
    }
}