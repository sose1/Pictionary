package pl.sose1.game

import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User

sealed class GameViewEvent {
    class SetMessage(val message: Message, val user: User) : GameViewEvent()
    class SetGameCode(val code: String) : GameViewEvent()
    class DrawBitmap(val byteArray: ByteArray) : GameViewEvent()
    class GameStarted(val isStarted: Boolean) : GameViewEvent()
    class Painter(val wordGuess: String) : GameViewEvent()
    class Guessing(val wordGuessInUnder: String) : GameViewEvent()

    object ClearMessageContentText : GameViewEvent()
    object ChangeBrushColor : GameViewEvent()
    object ShowTimeoutException : GameViewEvent()
    object ClearImage : GameViewEvent()
}