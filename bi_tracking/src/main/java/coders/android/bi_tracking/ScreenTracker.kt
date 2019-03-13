package coders.android.bi_tracking

import timber.log.Timber

/**
 * @author msahakyan.
 */
class ScreenTracker : Tracker {

    override fun track(body: Any) {
        Timber.d("ScreenTracker [$body] -> $this")
    }
}