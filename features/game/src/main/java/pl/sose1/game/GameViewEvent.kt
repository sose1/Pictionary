package pl.sose1.game

import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User

sealed class GameViewEvent {
    class SetMessage(val message: Message, val user: User) : GameViewEvent()
    class DrawBitmap(val byteArray: ByteArray) : GameViewEvent()
    object ChangeBrushColor : GameViewEvent()
    object ClearImage : GameViewEvent()
    object ShowTimeoutException : GameViewEvent()
    object ClearMessageContentText : GameViewEvent()
    object ShowPaintingInfoAnimation : GameViewEvent()
    object ShowGuessingInfoAnimation : GameViewEvent()
    object ShowWordGuessAndPaintingInfoAnimation : GameViewEvent()
    object ShowWordGuessAndGuessingInfoAnimation : GameViewEvent()
}