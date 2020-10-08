package pl.sose1.home

import pl.sose1.core.model.lobby.LobbyEvent

sealed class HomeViewEvent {
    object OnClickCreateButton : HomeViewEvent()
    object ShowUserNameError : HomeViewEvent()
    object ShowInputFieldError : HomeViewEvent()
    class OpenLobby(val e: LobbyEvent.Registered) : HomeViewEvent()
}