package coders.android.couroutines.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coders.android.bi_tracking.service.TrackingService
import coders.android.couroutines.R
import coders.android.couroutines.viewmodel.UserViewModel
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_detail.detail_avatar
import kotlinx.android.synthetic.main.activity_detail.error_container
import kotlinx.android.synthetic.main.activity_detail.progress
import kotlinx.android.synthetic.main.activity_detail.user_login
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.getActivityScope
import org.koin.androidx.viewmodel.ext.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<UserViewModel>()
    private val tracingService by getActivityScope().inject<TrackingService>(name = "DetailActivity")
    private val glide by inject<RequestManager>()

    private lateinit var login: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // bind "activity" scope to component lifecycle
        bindScope(getActivityScope())
        login = intent.getStringExtra(KEY_USER_LOGIN)
        observeUser()
    }

    override fun onResume() {
        super.onResume()
        tracingService.trackEvent(DetailActivity::class.java.simpleName)
        viewModel.fetchUser(login)
    }

    private fun observeUser() {
        viewModel.state.observe(this, Observer {
            render(it)
        })
    }

    private fun render(state: UserViewModel.UserState) {
        when (state) {
            is UserViewModel.UserState.Loading -> {
                progress.visible = true
                detail_avatar.visible = false
                user_login.visible = false
            }
            is UserViewModel.UserState.Error -> {
                error_container.visible = true
                error_container.text = state.throwable.message
                progress.visible = false
                user_login.visible = false
            }
            is UserViewModel.UserState.UserLoaded -> {
                progress.visible = false
                error_container.visible = false
                with(user_login) {
                    visible = true
                    text = state.user.login
                }
                with(detail_avatar) {
                    glide.load(state.user.avatar_url)
                        .into(this)
                    visible = true
                }
            }
        }
    }
}
