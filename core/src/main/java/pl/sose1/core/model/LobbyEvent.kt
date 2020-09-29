package pl.sose1.core.model

sealed class LobbyEvent {
    class Created(val id: String, val code: String) : LobbyEvent()
}