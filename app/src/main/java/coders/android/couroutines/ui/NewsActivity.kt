package coders.android.couroutines.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import coders.android.bi_tracking.service.TrackingService
import coders.android.couroutines.R
import coders.android.couroutines.model.Article
import coders.android.couroutines.viewmodel.FeedViewModel
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.activity_news.error_container
import kotlinx.android.synthetic.main.activity_news.progress
import kotlinx.android.synthetic.main.activity_news.recycler_view
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.bindScope
import org.koin.androidx.scope.getActivityScope
import org.koin.androidx.viewmodel.ext.viewModel


class NewsActivity : AppCompatActivity() {

    private val viewModel by viewModel<FeedViewModel>()
    private val glide by inject<RequestManager>()
    private val tracingService by getActivityScope().inject<TrackingService>(name = "NewsActivity")
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        bindScope(getActivityScope())
        setupAdapter()
        observeNews()
    }

    private fun setupAdapter() {
        val layoutManager = GridLayoutManager(this, 1)
        adapter = NewsAdapter(glide, ::openBrowser)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchNews("Armenia", perPage = 50)
        tracingService.trackEvent(NewsActivity::class.java.simpleName)
    }

    private fun observeNews() {
        viewModel.state.observe(this, Observer {
            render(it)
        })
    }

    private fun openBrowser(article: Article?) {
        val url = article?.url ?: return
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun render(state: FeedViewModel.FeedState) =
        when (state) {
            is FeedViewModel.FeedState.Loading -> {
                progress.visible = true
                recycler_view.visible = false
                error_container.visible = false
            }
            is FeedViewModel.FeedState.Error -> {
                error_container.visible = true
                error_container.text = state.throwable.message
                progress.visible = false
                recycler_view.visible = false
            }
            is FeedViewModel.FeedState.NewsLoaded -> {
                progress.visible = false
                error_container.visible = false
                recycler_view.visible = true
                adapter.items = state.news
                adapter.notifyDataSetChanged()
            }
        }
}
