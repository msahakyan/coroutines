package coders.android.couroutines.api

import coders.android.couroutines.model.SearchResults
import coders.android.couroutines.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author msahakyan.
 */
interface UserApi {

    @GET("users")
    fun fetchUsersAsync(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Deferred<List<User>>

    @GET("users/{login}")
    fun fetchUserByLoginAsync(
        @Path("login") login: String
    ): Deferred<User>

    @GET("search/users")
    fun searchAsync(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 30
    ): Deferred<SearchResults>
}