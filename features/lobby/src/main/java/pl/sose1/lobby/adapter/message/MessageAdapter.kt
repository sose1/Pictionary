package pl.sose1.lobby.adapter.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sose1.core.model.lobby.Message
import pl.sose1.lobby.databinding.ItemMessageIncomingBinding
import pl.sose1.lobby.databinding.ItemMessageSendBinding

class MessageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages: List<Message> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            1 -> MessageSendViewHolder(
                ItemMessageSendBinding
                    .inflate(inflater, parent, false)
            )
            else -> MessageIncomingViewHolder(
                ItemMessageIncomingBinding
                    .inflate(inflater, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            1 -> (holder as MessageSendViewHolder).bind(messages[position])
            else -> (holder as MessageIncomingViewHolder).bind(messages[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].messageType == "send") 1 else 2
    }

    override fun getItemCount(): Int = messages.size

    fun setMessages(messages: List<Message>) {
        this.messages = messages
        notifyItemInserted(itemCount)
    }
}