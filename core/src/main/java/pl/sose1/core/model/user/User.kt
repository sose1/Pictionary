package pl.sose1.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: String,
    val name: String,
    val sessionId: String
)