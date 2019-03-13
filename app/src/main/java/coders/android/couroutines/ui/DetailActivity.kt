package coders.android.couroutines.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coders.android.bi_tracking.service.TrackingService
import coders.android.couroutines.R
import coders.android.couroutines.viewmodel.UserViewModel
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.getActivityScope
import org.koin.androidx.viewmodel.ext.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<UserViewModel>()
    private val tracingService by getActivityScope().inject<TrackingService>(name = "DetailActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // bind "activity" scope to component lifecycle
        bindScope(getActivityScope())
//        val login = intent.getStringExtra(KEY_USER_LOGIN)
    }

    override fun onResume() {
        super.onResume()
        tracingService.trackEvent(DetailActivity::class.java.simpleName)
    }
}
