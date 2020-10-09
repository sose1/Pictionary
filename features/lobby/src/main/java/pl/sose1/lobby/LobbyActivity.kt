package pl.sose1.lobby

import android.view.View
import androidx.lifecycle.observe
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.sose1.base.view.BaseActivity
import pl.sose1.lobby.adapter.UserAdapter
import pl.sose1.lobby.databinding.ActivityLobbyBinding


class LobbyActivity : BaseActivity<ActivityLobbyBinding, LobbyViewModel>(layoutId = R.layout.activity_lobby) {

    private lateinit var lobbyId: String
    private lateinit var userId: String
    private lateinit var creatorId: String
    private lateinit var code: String

    private val userAdapter = UserAdapter()

    override val viewModel by viewModel<LobbyViewModel> {
        parametersOf(lobbyId, userId, creatorId)
    }

    override fun onInitDataBinding() {
        lobbyId = intent.extras?.getString("LOBBY_ID") ?: throw NullPointerException("LOBBY_ID is null")
        userId = intent.extras?.getString("USER_ID") ?: throw NullPointerException("USER_ID is null")
        creatorId = intent.extras?.getString("CREATOR_ID") ?: throw NullPointerException("CREATOR_ID is null")
        code = intent.extras?.getString("LOBBY_CODE") ?: throw NullPointerException("CREATOR_ID is null")

        binding.viewModel = viewModel
        binding.list.adapter = userAdapter

        viewModel.events.observe(this, this::onViewEvent)

        this.supportActionBar?.subtitle = code
        viewModel.connectToLobby()
    }

    private fun onViewEvent(event: LobbyViewEvent) {
        when (event) {
            LobbyViewEvent.StartButtonIsInvisible -> startButtonIsInvisible()
            is LobbyViewEvent.SetUsers -> userAdapter.setUsers(event.e.users)

        }
    }

    private fun startButtonIsInvisible() {
        binding.startBtn.visibility = View.GONE
    }
}