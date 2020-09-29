package pl.sose1.home.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.sose1.home.HomeViewModel

val homeModule = module {
    viewModel { HomeViewModel(get()) }
}