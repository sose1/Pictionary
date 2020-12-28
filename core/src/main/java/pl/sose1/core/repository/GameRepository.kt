package pl.sose1.core.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.toByteString
import pl.sose1.core.model.game.*
import timber.log.Timber
import java.io.IOException

class GameRepository(private val gameId: String) {

    private var client = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                private val cookieStore: HashMap<HttpUrl, List<Cookie>> = HashMap()
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                    cookieStore[url] = cookies
                }

                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    val cookies = cookieStore[url]
                    return cookies ?: ArrayList()
                }
            })
            .build()

    private var request = Request.Builder().url("ws://192.168.0.2:8080/game/$gameId")
    private lateinit var webSocket: WebSocket

    val messageChannel = Channel<GameResponse>(1)
    val byteArrayChannel = Channel<ByteArray>(Channel.BUFFERED)

    fun connectToGame(userName: String) {
        webSocket = client.newWebSocket(request.addHeader("UserName", userName).build(), SocketListener())
    }

    fun sendMessage(messageContent: String) {
        webSocket.send(Json.encodeToString(SendMessage(messageContent) as GameRequest))
    }


    fun sendByteArray(byteArray: ByteArray) {
        webSocket.send(byteArray.toByteString())
    }

    fun getGameById() {
        request = Request.Builder()
                .url("http://192.168.0.2:8080/game/$gameId")

        client.newCall(request.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseString: String = response.body?.string().toString()
                    val game = Json.decodeFromString<Game>(responseString)
                    val gameById = GameById(game) as GameResponse

                    GlobalScope.launch {
                        messageChannel.send(gameById)
                    }
                }
            }
        })
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