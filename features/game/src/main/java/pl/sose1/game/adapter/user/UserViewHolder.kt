package pl.sose1.game.adapter.user

import androidx.recyclerview.widget.RecyclerView
import pl.sose1.core.model.user.User
import pl.sose1.game.databinding.ItemUserBinding

class UserViewHolder(
    private val binding: ItemUserBinding
)  : RecyclerView.ViewHolder(binding.root){

    fun bind(user: User?) {
        binding.user = user
        binding.executePendingBindings()
    }
}