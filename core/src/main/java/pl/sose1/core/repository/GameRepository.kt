package pl.sose1.core.repository

import android.graphics.Bitmap
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import okio.ByteString.Companion.toByteString
import pl.sose1.core.model.game.GameRequest
import pl.sose1.core.model.game.GameResponse
import pl.sose1.core.model.game.SendMessage
import pl.sose1.core.network.RetrofitBuilder
import timber.log.Timber
import java.io.ByteArrayOutputStream

class GameRepository(private val gameId: String) {

    private var client = RetrofitBuilder.client
    private var service = RetrofitBuilder.apiService

    private var request = Request.Builder().url("ws://192.168.0.2:8080/game/$gameId")
    private lateinit var webSocket: WebSocket

    val messageChannel = Channel<GameResponse>(1)
    val byteArrayChannel = Channel<ByteArray>(1)

    suspend fun getGameById() = service.getGameById(gameId)

    fun connectToGame(userName: String) {
        webSocket = client.newWebSocket(request.addHeader("UserName", userName).build(), SocketListener())
    }

    fun sendMessage(messageContent: String) {
        webSocket.send(Json.encodeToString(SendMessage(messageContent) as GameRequest))
    }


    fun sendBitmap(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()
        webSocket.send(byteArray.toByteString())
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
            Timber.d("onFailure: ${t.message}, $response, ${t.cause}")

        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            Timber.d("onMessage-TEXT: $text")

            val response: GameResponse = Json.decodeFromString(text)

            GlobalScope.launch {
                messageChannel.send(response)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            Timber.d("onMessage-BYTES: $bytes")
            val byteArray = bytes.toByteArray()

            GlobalScope.launch {
                byteArrayChannel.send(byteArray)
            }
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Timber.d("onOpen: $response")
        }
    }
}