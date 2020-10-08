package pl.sose1.lobby

import pl.sose1.core.model.lobby.LobbyEvent

sealed class LobbyViewEvent {
    class SetUsers(val e: LobbyEvent.Connected) : LobbyViewEvent()
}