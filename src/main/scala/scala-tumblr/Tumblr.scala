package io.github.petesta.tumblr

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Method, Request, RequestBuilder, Response}
import com.twitter.io.Buf
import com.twitter.util.Future

sealed trait Tumblr {
  protected val root = "api.tumblr.com"

  val path: String
  val params: Option[Map[String, String]]

  protected val client = Http.client.withTls(root).newService(s"$root:443")
  protected val keyValuePairs =
    params.map { opt =>
      "&"  + opt.view.map { case (key, value) => key + "=" + value }.mkString("&")
    }.getOrElse("")

  def get: Future[Response]
}

final case class Avatar(blogName: String, size: Int) extends Tumblr {
  val path = s"/v2/blog/$blogName.tumblr.com/avatar"
  val params: Option[Map[String, String]] = None

  def get = client(Request(Method.Get, s"$path?size=$size"))
}

sealed trait ApiKey extends Tumblr {
  private val apiKeyEnv = sys.env.get("api_key")

  protected val apiKey = s"?api_key=${apiKeyEnv.getOrElse("")}"

  def get = client(Request(Method.Get, s"$path$apiKey$keyValuePairs"))
}

final case class Info(blogName: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = s"/v2/blog/$blogName.tumblr.com/info"
}

final case class Likes(blogName: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = s"/v2/blog/$blogName.tumblr.com/likes"
}

final case class Posts(blogName: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = s"/v2/blog/$blogName.tumblr.com/photos"
}

final case class Tagged(tag: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = "/v2/tagged"

  override def get = client(Request(Method.Get, s"$path$apiKey&tag=$tag$keyValuePairs"))
}

sealed trait OAuth extends ApiKey {
  private val oauthToken = sys.env.get("oauth_token")
  private val oauthTokenSecret = sys.env.get("oauth_token_secret")

  protected val oauthParams =
    s"&${oauthToken.getOrElse("")}=${oauthTokenSecret.getOrElse("")}$keyValuePairs"

  override def get = client(Request(Method.Get, s"$path$apiKey$oauthParams"))

  protected val url = s"$root$path$apiKey$oauthParams$keyValuePairs"

  private val request =
    RequestBuilder()
      .url(url)
      .proxied()
      .buildPost(Buf.Utf8(""))

  def post = client(request)
}

final case class Following(blogName: String, params: Option[Map[String, String]] = None) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/following"
}

final case class Followers(blogName: String, params: Option[Map[String, String]] = None) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/followers"
}

final case class PostsQueue(blogName: String, params: Option[Map[String, String]] = None) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/posts/queue"
}

final case class PostsDraft(blogName: String, params: Option[Map[String, String]] = None) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/posts/draft"
}

final case class PostsSubmission(blogName: String, params: Option[Map[String, String]] = None) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/posts/submission"
}

final case class UserInfo(params: Option[Map[String, String]] = None) extends OAuth {
  val path = "/v2/user/info"
}

final case class UserDashboard(params: Option[Map[String, String]] = None) extends OAuth {
  val path = "/v2/user/dashboard"
}

final case class UserLikes(params: Option[Map[String, String]] = None) extends OAuth {
  val path = "/v2/user/likes"
}

final case class UserFollowing(params: Option[Map[String, String]] = None) extends OAuth {
  val path = "/v2/user/following"
}

final case class DeletePost(blogName: String, id: Long) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/blog/$blogName.tumblr.com/post/delete"

  override protected val url = s"$path$apiKey$oauthParams&id=$id"
}

final case class FollowUser(blogUrl: String) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/follow"

  override protected val url = s"$path$apiKey$oauthParams&url=$blogUrl"
}

final case class UnollowUser(blogUrl: String) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/unfollow"

  override protected val url = s"$path$apiKey$oauthParams&url=$blogUrl"
}

final case class LikePost(id: Long, reblogKey: Long) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/like"

  override protected val url = s"$path$apiKey$oauthParams&id=$id&reblog_key=$reblogKey"
}

final case class UnlikePost(id: Long, reblogKey: Long) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/unlike"

  override protected val url = s"$path$apiKey$oauthParams&id=$id&reblog_key=$reblogKey"
}

final case class CreatePost(
  blogName: String,
  posts: PostType,
  params: Option[Map[String, String]] = None
) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/post"

  override protected val url = s"$path$apiKey${posts.`type`}$keyValuePairs"
}

final case class EditPost(
  blogName: String,
  posts: PostType,
  params: Option[Map[String, String]] = None
) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/post/edit"

  override protected val url = s"$path$apiKey${posts.`type`}$keyValuePairs"
}

final case class ReblogPost(
  blogName: String,
  id: Long,
  reblogKey: Long,
  posts: PostType,
  params: Option[Map[String, String]]
) extends OAuth {
  val path = s"/v2/blog/$blogName.tumblr.com/post/reblog"

  override protected val url = s"$path$apiKey${posts.`type`}&id=$id&reblog_key=$reblogKey"
}
