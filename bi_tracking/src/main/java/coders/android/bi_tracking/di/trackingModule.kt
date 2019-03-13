package coders.android.bi_tracking.di

import coders.android.bi_tracking.ScreenTracker
import coders.android.bi_tracking.Tracker
import coders.android.bi_tracking.service.TrackingService
import org.koin.dsl.module

/**
 * @author msahakyan.
 */
val trackingModule = module {

    factory { ScreenTracker() as Tracker }

    scope("coders.android.couroutines.ui.MainActivity") {
        scoped(name = "MainActivity") { TrackingService(tracker = get()) }
    }

    scope("coders.android.couroutines.ui.DetailActivity") {
        scoped(name = "DetailActivity") { TrackingService(tracker = get()) }
    }

    scope("coders.android.couroutines.ui.NewsActivity") {
        scoped(name = "NewsActivity") { TrackingService(tracker = get()) }
    }
}