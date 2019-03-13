package coders.android.couroutines.di

import android.content.Context
import coders.android.couroutines.viewmodel.FeedViewModel
import coders.android.couroutines.viewmodel.UserListViewModel
import coders.android.couroutines.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author msahakyan.
 */

val appModule = module {

    viewModel { UserListViewModel(userRepository = get()) }

    viewModel { UserViewModel(userRepository = get()) }

    viewModel { FeedViewModel(newsRepository = get()) }

    single { Glide.with(get<Context>()) }
}