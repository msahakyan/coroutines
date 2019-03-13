package coders.android.bi_tracking.service

import coders.android.bi_tracking.Tracker

/**
 * @author msahakyan.
 */
class TrackingService(private val tracker: Tracker) {

    fun trackEvent(event: String) = tracker.track("$this -- $event")
}