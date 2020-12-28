package pl.sose1.game.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.sose1.game.GameViewModel
import pl.sose1.ui.PaintingView

val gameModule = module {
    viewModel { (gameId: String, paintingView: PaintingView) -> GameViewModel(gameId, paintingView) }
}