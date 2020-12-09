package pl.sose1.core.model.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed class GameRequest

@Serializable
@SerialName("SendMessage")
class SendMessage(
        val text: String
) : GameRequest(), java.io.Serializable

