package coders.android.couroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * @author msahakyan.
 */
val DispatcherIO = CoroutineScope(Dispatchers.IO)
val DispatcherUI = CoroutineScope(Dispatchers.Main)