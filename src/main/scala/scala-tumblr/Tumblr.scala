package io.github.petesta.tumblr

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Method, Request, RequestBuilder, Response}
import com.twitter.util.Future

sealed trait Tumblr {
  private val root = "api.tumblr.com/v2"

  val path: String
  val params: Option[Map[String, String]]

  protected val client = Http.client.withTls(root).newService(s"$root:443")
  protected val keyValuePairs =
    params.map { opt =>
      "&"  + opt.view.map { case (key, value) => key + "=" + value }.mkString("&")
    }.getOrElse("")

  def get: Future[Response]
}

sealed trait ApiKey extends Tumblr {
  private val apiKeyEnv = sys.env.get("api_key")

  protected val apiKey = s"?api_key=${apiKeyEnv.getOrElse("")}"

  def get = client(Request(Method.Get, s"$path$apiKey$keyValuePairs"))
}

final case class Info(blogName: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = s"/blog/$blogName.tumblr.com/info"
}

final case class Likes(blogName: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = s"/blog/$blogName.tumblr.com/likes"
}

final case class Posts(blogName: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = s"/blog/$blogName.tumblr.com/photos"
}

final case class Tagged(tag: String, params: Option[Map[String, String]] = None) extends ApiKey {
  val path = "/tagged"

  override def get = client(Request(Method.Get, s"$path$apiKey&tag=$tag$keyValuePairs"))
}

final case class Avatar(size: Int) extends Tumblr {
  val path = "/avatar"
  val params: Option[Map[String, String]] = None

  def get = client(Request(Method.Get, s"$path?size=$size"))
}
