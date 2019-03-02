package coders.android.couroutines.api

import coders.android.couroutines.model.Feed
import coders.android.couroutines.repository.NetworkConfig.News.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author msahakyan.
 */
interface NewsApi {

    @GET("v2/everything")
    fun fetchNewsAsync(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("pageSize") perPage: Int,
        @Query("apiKey") apiKey: String = API_KEY
    ): Deferred<Feed>
}