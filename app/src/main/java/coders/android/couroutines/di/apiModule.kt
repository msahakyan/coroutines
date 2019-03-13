package coders.android.couroutines.di

/**
 * @author msahakyan.
 */
import coders.android.couroutines.api.NewsApi
import coders.android.couroutines.api.UserApi
import coders.android.couroutines.repository.NetworkConfig
import coders.android.couroutines.repository.NewsRepository
import coders.android.couroutines.repository.UserRepository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val apiModule = module {

    single { createOkHttpClient() }

    // Github
    single(name = "github") { NetworkConfig.Github.BASE_URL }

    single {
        createWebService<UserApi>(
            okHttpClient = get(),
            url = get(name = "github")
        )
    }

    single {
        UserRepository(
            userApi = get()
        )
    }

    // News
    single(name = "news") { NetworkConfig.News.BASE_URL }

    single {
        createWebService<NewsApi>(
            okHttpClient = get(),
            url = get(name = "news")
        )
    }

    single {
        NewsRepository(
            newsApi = get()
        )
    }
}

private fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
    return OkHttpClient.Builder()
        .connectTimeout(NetworkConfig.Timeout.CONNECTION, TimeUnit.SECONDS)
        .readTimeout(NetworkConfig.Timeout.READ, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}

private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    return retrofit.create(T::class.java)
}