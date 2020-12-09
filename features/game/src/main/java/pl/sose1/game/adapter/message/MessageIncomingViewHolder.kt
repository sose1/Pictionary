package pl.sose1.game.adapter.message

import androidx.recyclerview.widget.RecyclerView
import pl.sose1.core.model.game.Message
import pl.sose1.game.databinding.ItemMessageIncomingBinding

class MessageIncomingViewHolder(
    private val binding: ItemMessageIncomingBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(message: Message?) {
        binding.message = message
        binding.executePendingBindings()
    }
}