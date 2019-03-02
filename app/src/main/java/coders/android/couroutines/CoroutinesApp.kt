package coders.android.couroutines

import android.app.Application
import coders.android.couroutines.di.apiModule
import coders.android.couroutines.di.appModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 * @author msahakyan.
 */
class CoroutinesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
        initializeTimber()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeKoin() {
        startKoin(
            androidContext = this@CoroutinesApp,
            modules = listOf(appModule, apiModule),
            extraProperties = mapOf("key" to "Pass123456!")
        )
    }
}