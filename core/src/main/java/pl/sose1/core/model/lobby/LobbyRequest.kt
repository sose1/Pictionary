package pl.sose1.core.model.lobby

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class LobbyRequest

@Serializable
@SerialName("Register")
class Register(
        val userName: String,
        val code: String? = null
) : LobbyRequest()

@Serializable
@SerialName("Create")
class Create(
        val userName: String
) : LobbyRequest()

@Serializable
@SerialName("Connect")
class Connect(
        val userId: String,
) : LobbyRequest()

@Serializable
@SerialName("Message")
class Message(
        val text: String,
        val authorId: String,
        var messageType: String,
        val authorName: String,
) : LobbyRequest()

