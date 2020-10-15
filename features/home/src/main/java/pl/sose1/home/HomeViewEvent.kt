package pl.sose1.home

import pl.sose1.core.model.lobby.Registered

sealed class HomeViewEvent {
    object OnClickCreateButton : HomeViewEvent()
    object ShowUserNameError : HomeViewEvent()
    object ShowInputFieldError : HomeViewEvent()
    class OpenLobby(val e: Registered) : HomeViewEvent()
}