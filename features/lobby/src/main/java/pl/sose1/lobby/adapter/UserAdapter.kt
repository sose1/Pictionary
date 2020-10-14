package pl.sose1.lobby.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.sose1.core.model.user.User
import pl.sose1.lobby.databinding.ItemUserBinding
import timber.log.Timber

class UserAdapter: RecyclerView.Adapter<UserViewHolder>() {

    private var users: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater =  LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun setUsers(users: List<User>) {
        this.users = users
        Timber.d("SetUsers: $users")

        notifyDataSetChanged()
    }
}