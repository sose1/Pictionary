package pl.sose1.lobby.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.sose1.lobby.LobbyViewModel

val lobbyModule = module {
    viewModel { (lobbyId: String, userId: String, userName: String) ->
        LobbyViewModel(lobbyId, userId, userName) }
}