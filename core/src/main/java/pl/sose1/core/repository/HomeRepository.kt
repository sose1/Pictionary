package pl.sose1.core.repository

import pl.sose1.core.model.exception.NotFoundException
import pl.sose1.core.model.game.Game
import pl.sose1.core.network.RetrofitBuilder
import retrofit2.HttpException

class HomeRepository {
    private var service = RetrofitBuilder.apiService

    suspend fun getEmptyGame() = service.getEmptyGame()

    suspend fun  getGameByCode(code: String): Game {
        return try {
            service.getGameByCode(code)
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> throw NotFoundException()
                else -> throw Exception()
            }
        }
    }
}