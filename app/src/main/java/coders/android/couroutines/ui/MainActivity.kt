package coders.android.couroutines.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import coders.android.bi_tracking.service.TrackingService
import coders.android.couroutines.R
import coders.android.couroutines.viewmodel.UserListViewModel
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_main.error_container
import kotlinx.android.synthetic.main.activity_main.progress
import kotlinx.android.synthetic.main.activity_main.recycler_view
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.getActivityScope
import org.koin.androidx.viewmodel.ext.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<UserListViewModel>()
    private val glide by inject<RequestManager>()
    private val tracingService by getActivityScope().inject<TrackingService>(name = "MainActivity")
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // bind "activity" scope to component lifecycle
        bindScope(getActivityScope())
        setupAdapter()
        observeUsers()
    }

    private fun setupAdapter() {
        val layoutManager = GridLayoutManager(this, 2)
        adapter = UserAdapter(glide, ::onItemCLick)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers(perPage = 50)
        tracingService.trackEvent(MainActivity::class.java.simpleName)
    }

    private fun onItemCLick(login: String?) {
        Intent(this, DetailActivity::class.java)
            .apply { putExtra(KEY_USER_LOGIN, login) }
            .also { startActivity(it) }
    }

    private fun observeUsers() {
        viewModel.state.observe(this, Observer {
            render(it)
        })
    }

    private fun render(state: UserListViewModel.UserListState) {
        when (state) {
            is UserListViewModel.UserListState.Loading -> {
                progress.visible = true
                recycler_view.visible = false
                error_container.visible = false
            }
            is UserListViewModel.UserListState.Error -> {
                error_container.visible = true
                error_container.text = state.throwable.message
                progress.visible = false
                recycler_view.visible = false
            }
            is UserListViewModel.UserListState.UserListLoaded -> {
                progress.visible = false
                error_container.visible = false
                recycler_view.visible = true
                adapter.items = state.users
                adapter.notifyDataSetChanged()
            }
        }
    }
}

const val KEY_USER_LOGIN = "login"

var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
