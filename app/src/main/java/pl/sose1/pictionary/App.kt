package pl.sose1.pictionary

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.sose1.core.di.coreModule
import pl.sose1.game.di.gameModule
import pl.sose1.home.di.homeModule
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        configureTimber()
        configureKoin()
    }

    private fun configureTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun configureKoin() {
        startKoin{
            androidContext(this@App)
            modules(
                listOf(
                    coreModule,
                    homeModule,
                    gameModule
                )
            )
        }
    }
}