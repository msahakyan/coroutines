package coders.android.couroutines.di

import android.content.Context
import coders.android.couroutines.viewmodel.UserListViewModel
import coders.android.couroutines.viewmodel.UserViewModel
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * @author msahakyan.
 */

val appModule = module {

    viewModel { UserListViewModel(userRepository = get()) }

    viewModel { UserViewModel(userRepository = get()) }

    single { Glide.with(get<Context>()) }

//    factory { (items: List<User>) -> UserAdapter(items = items, glide = get()) }

}