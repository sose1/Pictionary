package pl.sose1.lobby

import pl.sose1.core.model.response.Users

sealed class LobbyViewEvent {
    class SetUsers(val e: Users) : LobbyViewEvent()
    object StartButtonIsInvisible : LobbyViewEvent()
}