package pl.sose1.lobby

import pl.sose1.core.model.lobby.Users

sealed class LobbyViewEvent {
    class SetUsers(val e: Users) : LobbyViewEvent()
    object StartButtonIsVisible : LobbyViewEvent()
    object StartButtonIsInvisible : LobbyViewEvent()
}