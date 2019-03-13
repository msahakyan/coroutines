package coders.android.couroutines.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coders.android.couroutines.DispatcherIO
import coders.android.couroutines.model.User
import coders.android.couroutines.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author msahakyan.
 */
class UserListViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val mutableState = MutableLiveData<UserListState>()
    val state: LiveData<UserListState>
        get() = mutableState

    private var job: Job? = null

    fun fetchUsers(page: Int = 1, perPage: Int = 30) {
        job = DispatcherIO.launch {
            try {
                mutableState.postValue(UserListState.Loading)
                val users = userRepository.fetchUsers(page, perPage)
                mutableState.postValue(UserListState.UserListLoaded(users))
            } catch (e: Exception) {
                mutableState.postValue(UserListState.Error(e))
            }
        }
    }

    sealed class UserListState {
        object Loading : UserListState()
        data class UserListLoaded(val users: List<User>) : UserListState()
        data class Error(val throwable: Throwable) : UserListState()
    }

    override fun onCleared() {
        job?.cancel().also { Timber.d(">>>> Canceling a job") }
    }
}