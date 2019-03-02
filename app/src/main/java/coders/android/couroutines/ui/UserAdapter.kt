package coders.android.couroutines.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coders.android.couroutines.R
import coders.android.couroutines.model.User
import com.bumptech.glide.RequestManager
import kotlinx.android.synthetic.main.list_item_user.view.avatar
import kotlinx.android.synthetic.main.list_item_user.view.login
import kotlinx.android.synthetic.main.list_item_user.view.score

/**
 * @author msahakyan.
 */
class UserAdapter(
    private val glide: RequestManager,
    private val listener: (login: String?) -> Unit
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var items: List<User>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = items?.get(position)
        val itemView = holder.itemView

        glide.load(user?.avatar_url)
            .into(itemView.avatar)
        itemView.avatar.setOnClickListener { listener.invoke(user?.login) }

        itemView.login.text = user?.login
        itemView.score.text = user?.score?.toString()
    }

    override fun getItemCount() = items?.size ?: 0

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}