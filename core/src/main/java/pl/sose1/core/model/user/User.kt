package pl.sose1.core.model.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val id: String,
) : java.io.Serializable