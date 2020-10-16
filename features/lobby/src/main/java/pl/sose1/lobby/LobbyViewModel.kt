package pl.sose1.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import pl.sose1.base.SingleLiveData
import pl.sose1.core.model.lobby.NewCreator
import pl.sose1.core.model.lobby.Users
import pl.sose1.core.repository.LobbyRepository

class LobbyViewModel(
    lobbyId: String,
    private val userId: String,
    private val creatorId: String): ViewModel(), KoinComponent {

    private val lobbyRepository by inject<LobbyRepository> {
        parametersOf(lobbyId)
    }

    val events = SingleLiveData<LobbyViewEvent>()

    init {
        checkCreatorId(creatorId)

        viewModelScope.launch {
            lobbyRepository.messageChannel.receiveAsFlow().collect { e ->
                when (e) {
                    is Users ->  events.value = LobbyViewEvent.SetUsers(e)
                    is NewCreator -> checkCreatorId(e.creatorId)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        lobbyRepository.close()
    }

    fun connectToLobby() {
        lobbyRepository.connectToLobby(userId)
    }

    private fun checkCreatorId(creatorId: String) {
        if (userId != creatorId) {
            events.value = LobbyViewEvent.StartButtonIsInvisible
        } else {
            events.value = LobbyViewEvent.StartButtonIsVisible
        }
    }
}