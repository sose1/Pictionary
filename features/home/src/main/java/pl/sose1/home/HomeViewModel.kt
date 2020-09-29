package pl.sose1.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.sose1.base.SingleLiveData
import pl.sose1.core.model.LobbyEvent
import pl.sose1.core.repository.LobbyRepository
import timber.log.Timber

class HomeViewModel(
    private val lobbyRepository: LobbyRepository
) : ViewModel() {

    val events = SingleLiveData<HomeViewEvent>()
    val userName = SingleLiveData<String>()
    val lobbyCode = SingleLiveData<String>()

    init {
        viewModelScope.launch {
            lobbyRepository.messageChannel.receiveAsFlow().collect { e ->
                when(e) {
                    is LobbyEvent.Created -> Timber.d(e.code)
                }
            }
        }
    }

    fun onClickCreateButton() {
        events.value = HomeViewEvent.OnClickCreateButton
    }

    fun onCLickConnectButton() {
        val userNameString = userName.value
        val lobbyCodeString = lobbyCode.value

        viewModelScope.launch {
            if (userNameString != null && lobbyCodeString != null) {
                lobbyRepository.connectToLobby(userNameString,lobbyCodeString)
                events.value = HomeViewEvent.OnClickConnectButton
            } else {
                events.value = HomeViewEvent.ShowInputFieldError
            }
        }
    }

    fun onClickPositiveButton() {
        val userNameString = userName.value
        viewModelScope.launch {
            if (userNameString != null) {
                lobbyRepository.createLobby(userNameString)
                events.value = HomeViewEvent.OnClickPositiveButton
            } else {
                events.value = HomeViewEvent.ShowUserNameError
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        lobbyRepository.close()
    }
}