package pl.sose1.core.repository

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.OkHttpClient
import pl.sose1.core.model.game.Game
import java.io.IOException


class HomeRepository {
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

    val messageChannel = Channel<Game>(1)

    fun getEmptyGame() {
        val request = Request.Builder()
                .url("http://192.168.0.2:8080/game/empty")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseString: String = response.body?.string().toString()
                    val game = Json.decodeFromString<Game>(responseString)

                    GlobalScope.launch {
                        messageChannel.send(game)
                    }
                }
            }
        })

    }

    fun getGameByCode(code: String) {
        val request = Request.Builder()
                .url("http://192.168.0.2:8080/game/code/$code")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseString: String = response.body?.string().toString()
                    val game = Json.decodeFromString<Game>(responseString)

                    GlobalScope.launch {
                        messageChannel.send(game)
                    }
                }
            }
        })
    }

    fun close() {
        messageChannel.cancel()
    }

}