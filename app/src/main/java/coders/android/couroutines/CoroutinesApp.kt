package coders.android.couroutines

import android.app.Application
import coders.android.bi_tracking.di.trackingModule
import coders.android.couroutines.di.apiModule
import coders.android.couroutines.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
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
        startKoin {
            androidContext(this@CoroutinesApp)
            modules(appModule, apiModule, trackingModule)
            mapOf("key" to "Pass123456!")

        }
    }
}