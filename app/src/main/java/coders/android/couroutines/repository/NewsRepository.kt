package coders.android.couroutines.repository

import coders.android.couroutines.api.NewsApi
import coders.android.couroutines.model.Feed

/**
 * @author msahakyan.
 */
class NewsRepository(
    private val newsApi: NewsApi
) : INewsRepository {
    override suspend fun fetchNews(q: String, page: Int, perPage: Int) =
        newsApi.fetchNewsAsync(q = q, page = page, perPage = perPage).await()
}

interface INewsRepository {

    /**
     * Fetches news from [coders.android.couroutines.api.NewsApi] by given search query [q].
     *
     * @param q is a search query
     * @param page is a page started from which results should be fetched
     * @param perPage is amount of results per page
     */
    suspend fun fetchNews(q: String, page: Int, perPage: Int): Feed
}