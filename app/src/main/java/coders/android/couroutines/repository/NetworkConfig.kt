package coders.android.couroutines.repository

/**
 * @author msahakyan.
 */
object NetworkConfig {

    object Github {
        const val BASE_URL = "https://api.github.com/"
    }

    object News {
        const val BASE_URL = "https://newsapi.org/"
        const val API_KEY = "079dac74a5f94ebdb990ecf61c8854b7"
    }

    object Timeout {
        const val CONNECTION = 60L // sec
        const val READ = 60L // sec
    }
}