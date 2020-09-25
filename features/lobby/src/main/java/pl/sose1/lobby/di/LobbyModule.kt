package pl.sose1.lobby.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val lobbyModule = module {
    viewModel { pl.sose1.lobby.LobbyViewModel() }
}