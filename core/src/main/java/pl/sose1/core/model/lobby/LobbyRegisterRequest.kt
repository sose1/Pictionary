package pl.sose1.core.model.lobby

import com.google.gson.GsonBuilder

class LobbyRegisterRequest(val userName: String, val eventName: String, val code: String? = null) {
    fun toJSON(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(this, LobbyRegisterRequest::class.java)
    }
}