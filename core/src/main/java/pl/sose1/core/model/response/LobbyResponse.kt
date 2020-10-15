package pl.sose1.core.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.sose1.core.model.user.User


@Serializable
sealed class LobbyResponse

@Serializable
@SerialName("Registered")
class Registered(
        val user: User,
        val lobbyId: String,
        val code: String,
        val creatorId: String
) : LobbyResponse()

@Serializable
@SerialName("Users")
class Users(
        val users: List<User>
) : LobbyResponse()
