package pl.sose1.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import pl.sose1.base.SingleLiveData
import pl.sose1.core.repository.HomeRepository

class HomeViewModel(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val events = SingleLiveData<HomeViewEvent>()
    val userName = SingleLiveData<String>()
    val gameCode = SingleLiveData<String>()

    init {
        viewModelScope.launch {
            homeRepository.messageChannel.receiveAsFlow().collect {
                events.value = userName.value?.let { userName -> HomeViewEvent.OpenLobby(it.id, userName) }
            }
        }
    }

    fun onClickCreateButton() {
        events.value = HomeViewEvent.OnClickCreateButton
    }

    fun onCLickConnectButton() {
        val userNameString = userName.value
        val gameCodeString = gameCode.value

        viewModelScope.launch {
            if (userNameString != null && gameCodeString != null) {
                homeRepository.getGameByCode(gameCodeString)
            } else {
                events.value = HomeViewEvent.ShowInputFieldError
            }
        }
    }

    fun onClickPositiveButton() {
        val userNameString = userName.value
        viewModelScope.launch {
            if (userNameString != null) {
                homeRepository.getEmptyGame()
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