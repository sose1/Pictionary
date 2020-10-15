package pl.sose1.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.sose1.base.SingleLiveData
import pl.sose1.core.model.lobby.Registered
import pl.sose1.core.repository.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val events = SingleLiveData<HomeViewEvent>()
    val userName = SingleLiveData<String>()
    val lobbyCode = SingleLiveData<String>()

    init {
        viewModelScope.launch {
            homeRepository.messageChannel.receiveAsFlow().collect { e ->
                when (e) {
                    is Registered -> events.value = HomeViewEvent.OpenLobby(e)
                    else -> { }
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
                homeRepository.registerToLobby(userNameString, lobbyCodeString)
            } else {
                events.value = HomeViewEvent.ShowInputFieldError
            }
        }
    }

    fun onClickPositiveButton() {
        val userNameString = userName.value
        viewModelScope.launch {
            if (userNameString != null) {
                homeRepository.createLobby(userNameString)
            } else {
                events.value = HomeViewEvent.ShowUserNameError
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeRepository.close()
    }
}