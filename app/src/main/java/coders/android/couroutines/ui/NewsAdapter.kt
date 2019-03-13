package coders.android.couroutines.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coders.android.couroutines.R
import coders.android.couroutines.model.Article
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.list_item_news.view.author
import kotlinx.android.synthetic.main.list_item_news.view.cover_image
import kotlinx.android.synthetic.main.list_item_news.view.description
import kotlinx.android.synthetic.main.list_item_news.view.title

/**
 * @author msahakyan.
 */
class NewsAdapter(
    private val glide: RequestManager,
    private val listener: (Article?) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    var items: List<Article>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_news, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = items?.get(position)
        val itemView = holder.itemView

        glide.load(article?.urlToImage)
            .into(itemView.cover_image)

        itemView.author.text = article?.author
        itemView.description.text = article?.description
        itemView.title.text = article?.title
        itemView.cover_image.setOnClickListener {
            listener.invoke(article)
        }
    }

    override fun getItemCount() = items?.size ?: 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}