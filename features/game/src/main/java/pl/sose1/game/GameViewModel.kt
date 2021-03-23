package pl.sose1.game

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import pl.sose1.base.SingleLiveData
import pl.sose1.core.model.game.*
import pl.sose1.core.model.user.User
import pl.sose1.core.repository.GameRepository
import pl.sose1.ui.painting.PathDrawnListener
import java.net.SocketTimeoutException

class GameViewModel(gameId: String): ViewModel(), KoinComponent, PathDrawnListener {

    private val gameRepository by inject<GameRepository> {
        parametersOf(gameId)
    }

    val events = SingleLiveData<GameViewEvent>()
    val messageContent = SingleLiveData<String>()
    var word = MutableLiveData("")
    var isPainter = MutableLiveData(false)
    var isStarted = MutableLiveData(false)
    var code = MutableLiveData("")
    var user: User = User("", "")
    var bottomSheetInfo = MutableLiveData("")

    init {
        viewModelScope.launch {
            gameRepository.messageChannel.receiveAsFlow().collect { e ->
                when (e) {
                    is NewUserAdded -> user = e.user
                    is Message -> events.value = GameViewEvent.SetMessage(e, user)
                    is GameStarted -> onGameStarted(e)
                    is FirstRoundStarted -> onFirstRoundStarted(e)
                    is NextRoundStarted -> onNextRoundStarted(e)
                }
            }
        }

        viewModelScope.launch {
            gameRepository.byteArrayChannel.receiveAsFlow().collect {
                events.value = GameViewEvent.DrawBitmap(it)
            }
        }
    }

    private fun onFirstRoundStarted(event: FirstRoundStarted) {
        word.postValue(event.newWordGuess)
        isPainter.postValue(event.isPainter)

        if (event.isPainter) {
            events.value = GameViewEvent.ShowPaintingInfoAnimation

        } else {
            events.value = GameViewEvent.ShowGuessingInfoAnimation
        }
    }

    private fun onNextRoundStarted(event: NextRoundStarted) {
        word.postValue(event.newWordGuess)
        isPainter.postValue(event.isPainter)
        bottomSheetInfo.postValue("${event.userNameWhoGuessed} zgadł hasło! \"${event.oldWordGuess.toLowerCase()}\"")
        if (event.isPainter) {
            events.value = GameViewEvent.ShowWordGuessAndPaintingInfoAnimation

        } else {
            events.value = GameViewEvent.ShowWordGuessAndGuessingInfoAnimation
        }
    }

    private fun onGameStarted(event: GameStarted) {
        isStarted.postValue(event.isStarted)

    }

    override fun onCleared() {
        super.onCleared()
        gameRepository.close()
    }

    fun connectToGame(userName: String) {
        gameRepository.connectToGame(userName)
    }

    fun getGameById() {
        viewModelScope.launch {
            try {
                val game = gameRepository.getGameById()
                code.postValue(game.code)

            } catch (e: Exception) {
                if (e is SocketTimeoutException) events.value = GameViewEvent.ShowTimeoutException
            }
        }
    }

    fun sendMessage() {
        val messageContentString = messageContent.value
        if (!messageContentString.isNullOrEmpty()) {
            gameRepository.sendMessage(messageContentString)
        }

        events.value = GameViewEvent.ClearMessageContentText
    }

    fun changeBrushColor() {
        events.value = GameViewEvent.ChangeBrushColor
    }

    fun clearImage() {
        events.value = GameViewEvent.ClearImage
    }

    override fun onPathDrawn(bitmap: Bitmap) {
        gameRepository.sendBitmap(bitmap)
    }
}