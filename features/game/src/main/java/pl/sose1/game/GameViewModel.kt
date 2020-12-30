package pl.sose1.game

import android.graphics.Bitmap
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
import pl.sose1.core.model.game.GameStarted
import pl.sose1.core.model.game.Message
import pl.sose1.core.model.game.NewUser
import pl.sose1.core.model.user.User
import pl.sose1.core.repository.GameRepository
import pl.sose1.ui.painting.PathDrawnListener
import timber.log.Timber

class GameViewModel(gameId: String): ViewModel(), KoinComponent, PathDrawnListener {

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
                    is Message -> events.value = GameViewEvent.SetMessage(e, user)
                    is GameById -> events.value = GameViewEvent.SetGameCodeInSubtitle(e.game.code)
                    is GameStarted -> events.value = GameViewEvent.GameStarted(e.isStarted)
                }
            }
        }

        viewModelScope.launch {
            gameRepository.byteArrayChannel.receiveAsFlow().collect {
                events.value = GameViewEvent.DrawBitmap(it)
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

    fun changeBrushColor() {
        Timber.d("CHANGE BRUSH COLOR")

        events.value = GameViewEvent.ChangeBrushColor
    }

    override fun onPathDrawn(bitmap: Bitmap) {
        gameRepository.sendBitmap(bitmap)
    }
}