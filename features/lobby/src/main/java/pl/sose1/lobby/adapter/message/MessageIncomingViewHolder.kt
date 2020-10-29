package pl.sose1.lobby.adapter.message

import androidx.recyclerview.widget.RecyclerView
import pl.sose1.core.model.lobby.Message
import pl.sose1.lobby.databinding.ItemMessageIncomingBinding

class MessageIncomingViewHolder(
    private val binding: ItemMessageIncomingBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(message: Message?) {
        binding.message = message
        binding.executePendingBindings()
    }
}