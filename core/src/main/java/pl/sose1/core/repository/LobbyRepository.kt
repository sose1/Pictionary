package pl.sose1.core.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import pl.sose1.core.model.LobbyEvent
import pl.sose1.core.model.LobbyRequestEventName
import pl.sose1.core.model.LobbyRequestMessage
import timber.log.Timber

class LobbyRepository {
    private val client = OkHttpClient()
    private val request = Request.Builder().url("ws://192.168.0.9:8080/lobby").build()
    private val webSocket = client.newWebSocket(request, SocketListener())

    val messageChannel = Channel<LobbyEvent>(1)

    fun createLobby(userNameString: String) {
        webSocket.send(LobbyRequestMessage(userNameString,
            LobbyRequestEventName.CREATE_LOBBY.name).toJSON())
        client.dispatcher.executorService.shutdown()
    }

    fun connectToLobby(userNameString: String, code: String) {
        webSocket.send(LobbyRequestMessage(userNameString,
            LobbyRequestEventName.CONNECT_TO_LOBBY.name, code).toJSON())
        client.dispatcher.executorService.shutdown()

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
            Timber.d("onFailure: ${t.message}, $response")

        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Timber.d("onMessage-TEXT: $text")
            GlobalScope.launch {
                messageChannel.send(LobbyEvent.Created("text","terxt"))
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