package coders.android.couroutines.model

data class User(
    val login: String,
    val id: Long,
    val avatar_url: String,
    val url: String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val starred_url: String,
    val gists_url: String,
    val type: String,
    val score: Float
)