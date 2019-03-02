package coders.android.couroutines.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coders.android.couroutines.R
import coders.android.couroutines.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val login = intent.getStringExtra(KEY_USER_LOGIN)
    }
}
