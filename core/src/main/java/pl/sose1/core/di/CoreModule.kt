package pl.sose1.core.di

import org.koin.dsl.module
import pl.sose1.core.repository.GameRepository
import pl.sose1.core.repository.HomeRepository

val coreModule = module {
    single { HomeRepository() }
    factory { (gameId: String) -> GameRepository(gameId) }
}