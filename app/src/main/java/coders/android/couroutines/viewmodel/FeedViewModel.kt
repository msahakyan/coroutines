package coders.android.couroutines.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coders.android.couroutines.DispatcherIO
import coders.android.couroutines.model.Article
import coders.android.couroutines.repository.NewsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author msahakyan.
 */
class FeedViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val mutableState = MutableLiveData<FeedState>()
    val state: LiveData<FeedState>
        get() = mutableState

    private var job: Job? = null

    fun fetchNews(q: String, page: Int = 1, perPage: Int = 20) {
        job = DispatcherIO.launch {
            try {
                mutableState.postValue(FeedState.Loading)
                val news = newsRepository.fetchNews(q, page, perPage).articles
                mutableState.postValue(FeedState.NewsLoaded(news))
            } catch (e: Exception) {
                mutableState.postValue(FeedState.Error(e))
            }
        }
    }

    sealed class FeedState {
        object Loading : FeedState()
        data class NewsLoaded(val news: List<Article>) : FeedState()
        data class Error(val throwable: Throwable) : FeedState()
    }

    override fun onCleared() {
        job?.cancel().also { Timber.d(">>>> Canceling a job: Loading Feed") }
    }
}