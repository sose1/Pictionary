package pl.sose1.game.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.sose1.game.GameViewModel

val gameModule = module {
    viewModel { (gameId: String) -> GameViewModel(gameId) }
}