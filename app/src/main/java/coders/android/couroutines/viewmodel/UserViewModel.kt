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
class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val mutableState = MutableLiveData<UserState>()
    val state: LiveData<UserState>
        get() = mutableState

    private var job: Job? = null

    @Suppress("unused")
    fun fetchUser(login: String) {
        job = DispatcherIO.launch {
            try {
                mutableState.postValue(UserState.Loading)
                val user = userRepository.fetchUserByLogin(login)
                mutableState.postValue(UserState.UserLoaded(user))
            } catch (e: Exception) {
                mutableState.postValue(UserState.Error(e))
            }
        }
    }

    sealed class UserState {
        object Loading : UserState()
        data class UserLoaded(val user: User) : UserState()
        data class Error(val throwable: Throwable) : UserState()
    }

    override fun onCleared() {
        job?.cancel().also { Timber.d(">>>> Canceling a fetchUser job") }
    }
}