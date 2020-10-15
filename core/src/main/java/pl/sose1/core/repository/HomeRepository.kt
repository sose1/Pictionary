package pl.sose1.core.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okio.ByteString
import pl.sose1.core.model.lobby.Create
import pl.sose1.core.model.lobby.LobbyRequest
import pl.sose1.core.model.lobby.Register
import pl.sose1.core.model.response.LobbyResponse
import timber.log.Timber

class HomeRepository {
    private val client = OkHttpClient()
    private val request = Request.Builder().url("ws://192.168.0.9:8080/lobby").build()
    private val webSocket = client.newWebSocket(request, SocketListener())
    val messageChannel = Channel<LobbyResponse>(1)

    private lateinit var lobbyRequest: LobbyRequest
    fun createLobby(userNameString: String) {
        lobbyRequest = Create(userNameString)
        webSocket.send(Json.encodeToString(lobbyRequest))
    }

    fun registerToLobby(userNameString: String, code: String) {
        lobbyRequest = Register(userNameString, code)
        webSocket.send(Json.encodeToString(lobbyRequest))
    }

    fun close() {
        webSocket.cancel()
        messageChannel.cancel()
    }

    inner class SocketListener : WebSocketListener() {
        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Timber.d("onClosed: $code, $reason")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosing(webSocket, code, reason)
            Timber.d("onClosing: $code, $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Timber.d("onFailure: $t")

        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Timber.d("onMessage-TEXT: $text")

            val response: LobbyResponse = Json.decodeFromString(text)

            GlobalScope.launch {
                messageChannel.send(response)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Timber.d("onMessage-BYTES: $bytes")
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Timber.d("onOpen: $response")
        }
    }
}