package pl.sose1.core.model.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pl.sose1.core.model.user.User

@Serializable
@SerialName("Game")
data class Game (
        val owner: User?,
        val id: String,
        val isStarted: Boolean,
        val code: String,
        val users: List<User>,
        val messages: List<Message>
) : java.io.Serializable