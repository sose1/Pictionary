package pl.sose1.lobby

import androidx.lifecycle.observe
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.sose1.base.view.BaseActivity
import pl.sose1.lobby.adapter.UserAdapter
import pl.sose1.lobby.databinding.ActivityLobbyBinding


class LobbyActivity : BaseActivity<ActivityLobbyBinding, LobbyViewModel>(layoutId = R.layout.activity_lobby) {

    override val viewModel by viewModel<LobbyViewModel> {
        parametersOf(lobbyId)
    }

    private lateinit var lobbyId: String
    private val userAdapter = UserAdapter()

    override fun onInitDataBinding() {
        lobbyId = intent.extras?.getString("LOBBY_ID") ?: throw NullPointerException("LOBBY_ID is null")

        binding.viewModel = viewModel
        binding.list.adapter = userAdapter

        viewModel.events.observe(this, this::onViewEvent)

        this.supportActionBar?.subtitle = intent.getStringExtra("LOBBY_CODE")
        viewModel.connectToLobby(intent.extras?.getString("USER_ID") ?: throw NullPointerException("USER_ID is null"))

    }

    private fun onViewEvent(event: LobbyViewEvent) {
        when (event) {
             is LobbyViewEvent.SetUsers -> userAdapter.setUsers(event.e.users)
        }
    }
}