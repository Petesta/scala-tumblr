package io.github.petesta.tumblr

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Method, Request, RequestBuilder, Response}
import com.twitter.util.Future

sealed trait Tumblr {
  private val root = "api.tumblr.com"

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

final case class RequestToken() extends Tumblr {
  val path = "/oauth/request_token"
}

final case class Authorize() extends Tumblr {
  val path = "/oauth/authorize"
}

final case class AccessToken() extends Tumblr {
  val path = "/oauth/access_token"
}

// TODO: OAuth
final case class Following(blogName: String, params: Option[Map[String, String]] = None) extends Tumblr {
  val path = s"/v2/blog/$blogName.tumblr.com/following"
}

// TODO: OAuth
final case class Followers(blogName: String, params: Option[Map[String, String]] = None) extends Tumblr {
  val path = s"/v2/blog/$blogName.tumblr.com/followers"
}

// TODO: OAuth
final case class PostsQueue(blogName: String, params: Option[Map[String, String]] = None) extends Tumblr {
  val path = s"/v2/blog/$blogName.tumblr.com/posts/queue"
}

// TODO: OAuth
final case class PostsDraft(blogName: String, params: Option[Map[String, String]] = None) extends Tumblr {
  val path = s"/v2/blog/$blogName.tumblr.com/posts/draft"
}

// TODO: OAuth
final case class PostsSubmission(blogName: String, params: Option[Map[String, String]] = None) extends Tumblr {
  val path = s"/v2/blog/$blogName.tumblr.com/posts/submission"
}

// TODO: OAuth
final case class UserInfo(params: Option[Map[String, String]] = None) extends Tumblr {
  val path = "/v2/user/info"
}

// TODO: OAuth
final case class UserDashboard(params: Option[Map[String, String]] = None) extends Tumblr {
  val path = "/v2/user/dashboard"
}

// TODO: OAuth
final case class UserLikes(params: Option[Map[String, String]] = None) extends Tumblr {
  val path = "/v2/user/likes"
}

// TODO: OAuth
final case class UserFollowing(params: Option[Map[String, String]] = None) extends Tumblr {
  val path = "/v2/user/following"
}
