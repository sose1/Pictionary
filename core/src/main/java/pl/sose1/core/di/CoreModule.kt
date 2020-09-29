package pl.sose1.core.di

import org.koin.dsl.module
import pl.sose1.core.repository.LobbyRepository

val coreModule = module {
    single { LobbyRepository() }
}