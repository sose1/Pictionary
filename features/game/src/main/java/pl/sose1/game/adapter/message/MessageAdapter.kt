package pl.sose1.game.adapter.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User
import pl.sose1.game.databinding.ItemMessageIncomingBinding
import pl.sose1.game.databinding.ItemMessageSendBinding
import pl.sose1.game.databinding.ItemMessageSystemBinding

class MessageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var messages: MutableList<Message> = mutableListOf()
    private lateinit var user: User
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            1 -> MessageSendViewHolder(
                    ItemMessageSendBinding
                            .inflate(inflater, parent, false)
            )
            2 -> MessageSystemViewHolder(
                    ItemMessageSystemBinding
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
            2 -> (holder as MessageSystemViewHolder).bind(messages[position])
            else -> (holder as MessageIncomingViewHolder).bind(messages[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].author.id == user.id ) 1 else if(messages[position].author.name == "SYSTEM") 2 else 3
    }

    override fun getItemCount(): Int = messages.size

    fun setMessages(message: Message, user: User) {
        this.user = user
        this.messages.add(message)

        notifyItemInserted(itemCount)
    }
}