package pl.sose1.lobby

import pl.sose1.core.model.lobby.Messages
import pl.sose1.core.model.lobby.Users

sealed class LobbyViewEvent {
    class SetUsers(val e: Users) : LobbyViewEvent()
    class SetMessages(val e: Messages) : LobbyViewEvent()
    object ClearMessageContentText : LobbyViewEvent()
    object StartButtonIsVisible : LobbyViewEvent()
    object StartButtonIsInvisible : LobbyViewEvent()
}