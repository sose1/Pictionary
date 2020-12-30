package pl.sose1.game

import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User

sealed class GameViewEvent {
    class SetMessage(val message: Message, val user: User) : GameViewEvent()
    class SetGameCodeInSubtitle(val code: String) : GameViewEvent()
    class DrawBitmap(val byteArray: ByteArray) : GameViewEvent()
    object ClearMessageContentText : GameViewEvent()
    object ChangeBrushColor : GameViewEvent()
    class GameStarted(val isStarted: Boolean) : GameViewEvent()
}