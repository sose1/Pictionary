package pl.sose1.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.sose1.base.SingleLiveData
import pl.sose1.core.model.exception.NotFoundException
import pl.sose1.core.repository.HomeRepository
import java.net.SocketTimeoutException

class HomeViewModel(
        private val homeRepository: HomeRepository
) : ViewModel() {

    val events = SingleLiveData<HomeViewEvent>()
    val userName = SingleLiveData<String>()
    val gameCode = SingleLiveData<String>()

    fun onClickCreateButton() {
        events.value = HomeViewEvent.OnClickCreateButton
    }

    fun onCLickConnectButton() {
        val userNameString = userName.value
        val gameCodeString = gameCode.value


        viewModelScope.launch {
            if (!userNameString.isNullOrBlank() && !gameCodeString.isNullOrBlank()) {
                try {
                    val game = homeRepository.getGameByCode(gameCodeString)
                    events.value = userName.value?.let {
                        HomeViewEvent.OpenLobby(game.id, it)
                    }
                }catch (e: Exception) {
                    e.printStackTrace()

                    when (e) {
                        is SocketTimeoutException -> events.value = HomeViewEvent.ShowTimeoutException
                        is NotFoundException -> events.value = HomeViewEvent.ShowNotFoundException
                    }
                }
            } else {
                events.value = HomeViewEvent.ShowInputFieldError
            }
        }
    }

    fun onClickPositiveButton() {
        val userNameString = userName.value
        viewModelScope.launch {
            if (!userNameString.isNullOrBlank()) {
                try {
                    val game = homeRepository.getEmptyGame()
                    events.value = userName.value?.let {
                        HomeViewEvent.OpenLobby(game.id, it)
                    }
                }catch (e: Exception) {
                    if (e is SocketTimeoutException) events.value = HomeViewEvent.ShowTimeoutException
                }
            } else {
                events.value = HomeViewEvent.ShowUserNameError
            }
        }
    }

    fun onClickInfoAboutCodeButton() {
        events.value = HomeViewEvent.onClickInfoAboutCodeButton
    }
}