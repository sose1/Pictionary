package pl.sose1.core.model.lobby

import com.google.gson.GsonBuilder

class LobbyConnectRequest(val userId: String, val eventName: String) {
    fun toJSON(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this, LobbyConnectRequest::class.java)
    }
}