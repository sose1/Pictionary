package pl.sose1.core.model.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.sose1.core.model.user.User


@Serializable
sealed class GameResponse

@Serializable
@SerialName("Message")
class Message(val content: String, val author: User ): GameResponse()

@Serializable
@SerialName("NewOwnerAdded")
class NewOwnerAdded(val user: User) : GameResponse()

@Serializable
@SerialName("NewUserAdded")
class NewUserAdded(val user: User) : GameResponse()

@Serializable
@SerialName("GameStarted")
class GameStarted(val isStarted: Boolean) : GameResponse()

@Serializable
@SerialName("FirstRoundStarted")
class FirstRoundStarted(val newWordGuess: String, val isPainter: Boolean ) : GameResponse()

@Serializable
@SerialName("NextRoundStarted")
class NextRoundStarted(
        val userNameWhoGuessed: String,
        val oldWordGuess: String,
        val newWordGuess: String,
        val isPainter: Boolean) : GameResponse()