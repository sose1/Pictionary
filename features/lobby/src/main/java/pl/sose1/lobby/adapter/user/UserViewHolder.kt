package pl.sose1.lobby.adapter.user

import androidx.recyclerview.widget.RecyclerView
import pl.sose1.core.model.user.User
import pl.sose1.lobby.databinding.ItemUserBinding

class UserViewHolder(
    private val binding: ItemUserBinding
)  : RecyclerView.ViewHolder(binding.root){

    fun bind(user: User?) {
        binding.user = user
        binding.executePendingBindings()
    }
}