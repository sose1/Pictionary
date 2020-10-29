package pl.sose1.lobby

import android.view.View
import androidx.lifecycle.observe
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import pl.sose1.base.view.BaseActivity
import pl.sose1.core.model.lobby.Message
import pl.sose1.lobby.adapter.message.MessageAdapter
import pl.sose1.lobby.adapter.user.UserAdapter
import pl.sose1.lobby.databinding.ActivityLobbyBinding


class LobbyActivity : BaseActivity<ActivityLobbyBinding, LobbyViewModel>(layoutId = R.layout.activity_lobby) {

    private lateinit var lobbyId: String
    private lateinit var userId: String
    private lateinit var creatorId: String
    private lateinit var code: String
    private lateinit var userName: String

    private val userAdapter = UserAdapter()
    private val messageAdapter = MessageAdapter()

    override val viewModel by viewModel<LobbyViewModel> {
        parametersOf(lobbyId, userId, userName)
    }

    override fun onInitDataBinding() {
        lobbyId = intent.extras?.getString("LOBBY_ID") ?: throw NullPointerException("LOBBY_ID is null")
        userId = intent.extras?.getString("USER_ID") ?: throw NullPointerException("USER_ID is null")
        creatorId = intent.extras?.getString("CREATOR_ID") ?: throw NullPointerException("CREATOR_ID is null")
        code = intent.extras?.getString("LOBBY_CODE") ?: throw NullPointerException("CREATOR_ID is null")
        userName = intent.extras?.getString("USER_NAME") ?: throw NullPointerException("USER_NAME is null")

        binding.viewModel = viewModel
        binding.users.adapter = userAdapter


        binding.messages.setHasFixedSize(true)
        binding.messages.adapter = messageAdapter

        viewModel.events.observe(this, this::onViewEvent)

        this.supportActionBar?.subtitle = code
        viewModel.connectToLobby()
    }

    private fun onViewEvent(event: LobbyViewEvent) {
        when (event) {
            LobbyViewEvent.StartButtonIsInvisible -> startButtonIsInvisible()
            LobbyViewEvent.StartButtonIsVisible -> startButtonIsVisible()
            LobbyViewEvent.ClearMessageContentText -> clearMessageContentText()
            is LobbyViewEvent.SetUsers -> userAdapter.setUsers(event.e.users)
            is LobbyViewEvent.SetMessages -> setMessages(event.e.messages)

        }
    }

    private fun setMessages(messages: List<Message>) {
        messageAdapter.setMessages(messages)
        if (messages.isNotEmpty()) {
            binding.messages.smoothScrollToPosition(messages.size - 1)
        }
    }

    private fun clearMessageContentText() {
        binding.messageContent.text.clear()
    }

    private fun startButtonIsInvisible() {
        binding.startBtn.visibility = View.GONE
    }

    private fun startButtonIsVisible() {
        binding.startBtn.visibility = View.VISIBLE
    }
}