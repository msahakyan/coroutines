package coders.android.couroutines.repository

import coders.android.couroutines.api.UserApi
import coders.android.couroutines.model.User

/**
 * @author msahakyan.
 */
class UserRepository(
    private val userApi: UserApi
) : IUserRepository {

    override suspend fun fetchUsers(page: Int, perPage: Int) =
        userApi.fetchUsersAsync(page, perPage).await()

    override suspend fun fetchUserByLogin(login: String): User =
        userApi.fetchUserByLoginAsync(login).await()

    override suspend fun searchUsers(query: String, page: Int, perPage: Int) =
        userApi.searchAsync(query, page, perPage).await().items
}

interface IUserRepository {

    /**
     * Fetches user results from the [coders.android.couroutines.api.UserApi]
     *
     * @param page is a page from where results should be fetched
     * @param perPage is amount of results per page (default value is 30)
     */
    suspend fun fetchUsers(page: Int, perPage: Int = 30): List<User>

    /**
     * Fetches user by given [login] from the [coders.android.couroutines.api.UserApi]
     *
     * @param login is a page from where results should be fetched
     */
    suspend fun fetchUserByLogin(login: String): User

    /**
     * Searches user results by given [query] from the [coders.android.couroutines.api.UserApi]
     *
     * @param query is a query needs to be found
     * @param page is a page from where results should be fetched
     * @param perPage is amount of results per page (default value is 30)
     */
    suspend fun searchUsers(query: String, page: Int, perPage: Int = 30): List<User>
}