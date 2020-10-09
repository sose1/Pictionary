package pl.sose1.core.model.lobby

import pl.sose1.core.model.user.User

sealed class LobbyEvent {
    class Registered(val user: User, val lobbyId: String, val code: String, val creatorId: String) : LobbyEvent()
    class Connected(val users: MutableList<User>) : LobbyEvent()

}