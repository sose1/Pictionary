package pl.sose1.core.network.service

import pl.sose1.core.model.game.Game
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("game/empty/")
    suspend fun getEmptyGame(): Game

    @GET("game/code/{code}")
    suspend fun getGameByCode(@Path("code") code: String): Game

    @GET("game/{id}")
    suspend fun getGameById(@Path("id") gameId: String): Game
}