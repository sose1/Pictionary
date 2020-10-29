package pl.sose1.core.model.lobby

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

@Serializable
@SerialName("NewCreator")
class NewCreator(
        val creatorId: String
) : LobbyResponse()

@Serializable
@SerialName("Error")
class Error(
        val errorCode: Int
) : LobbyResponse()

@Serializable
@SerialName("Messages")
class Messages(
        val messages: List<Message>
) : LobbyResponse()