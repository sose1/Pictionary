package pl.sose1.game

import pl.sose1.core.model.game.Message
import pl.sose1.core.model.user.User

sealed class GameViewEvent {
    class SetMessage(val message: Message, val user: User) : GameViewEvent()
    class SetGameCodeInSubtitle(val code: String) : GameViewEvent()
    object ClearMessageContentText : GameViewEvent()
}