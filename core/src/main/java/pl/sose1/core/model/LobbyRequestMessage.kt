package pl.sose1.core.model

import com.google.gson.GsonBuilder

class LobbyRequestMessage(val userName: String, val eventName: String, val code: String? = null) {
    fun toJSON(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this, LobbyRequestMessage::class.java)
    }
}