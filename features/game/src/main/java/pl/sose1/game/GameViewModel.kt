package pl.sose1.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import pl.sose1.base.SingleLiveData
import pl.sose1.core.model.game.GameById
import pl.sose1.core.model.game.Message
import pl.sose1.core.model.game.NewOwner
import pl.sose1.core.model.game.NewUser
import pl.sose1.core.model.user.User
import pl.sose1.core.repository.GameRepository

class GameViewModel(
        gameId: String): ViewModel(), KoinComponent {

    private val gameRepository by inject<GameRepository> {
        parametersOf(gameId)
    }


    val events = SingleLiveData<GameViewEvent>()
    val messageContent = SingleLiveData<String>()
    var user: User = User("", "")

    init {
        viewModelScope.launch {
            gameRepository.messageChannel.receiveAsFlow().collect { e ->
                when (e) {
                    is NewUser -> user = e.user
                    is NewOwner -> TODO()
                    is Message -> events.value = GameViewEvent.SetMessage(e, user)
                    is GameById -> events.value = GameViewEvent.SetGameCodeInSubtitle(e.game.code)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameRepository.close()
    }

    fun connectToGame(userName: String) {
        gameRepository.connectToGame(userName)
    }

    fun getGameById() {
        gameRepository.getGameById()
    }

    fun sendMessage() {
        val messageContentString = messageContent.value
        if (!messageContentString.isNullOrEmpty()) {
            gameRepository.sendMessage(messageContentString)
        }

        events.value = GameViewEvent.ClearMessageContentText
    }
}