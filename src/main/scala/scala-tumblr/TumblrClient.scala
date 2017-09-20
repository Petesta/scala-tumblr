package io.github.petesta.tumblr

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Method, Request, RequestBuilder, Response}
import com.twitter.io.Buf
import com.twitter.util.Future

private[tumblr] trait Tumblr {
  val path: String
  val params: Option[Map[String, String]]

  protected val root = "api.tumblr.com"
  protected val client = Http.client.withTls(root).newService(s"$root:443")
  protected val keyValuePairs =
    params.map { opt =>
      opt.view.map { case (key, value) => key + "=" + value }.mkString("&", "&", "")
    }.getOrElse("")

  protected def versionBlog(blogName: String) = s"/v2/blog/$blogName.tumblr.com"
}

private[tumblr] trait ApiKey extends Tumblr {
  implicit val apiConfig: ApiConfig

  protected val apiKey = s"?api_key=${apiConfig.apiKey}"

  def get = client(Request(Method.Get, s"$root$path$apiKey$keyValuePairs"))
}

private[tumblr] trait OAuth extends Tumblr {
  implicit val oauthConfig: OauthConfig

  protected val apiKey = s"?api_key=${oauthConfig.apiConfig.apiKey}"

  protected val oauthParams =
    s"&oauth_token=${oauthConfig.oauthToken}&oauth_token_secret=${oauthConfig.oauthTokenSecret}$keyValuePairs"

  protected val urlBuilder = s"https://$root$path$apiKey$oauthParams$keyValuePairs"
  protected val url = urlBuilder
}

private[tumblr] trait GET extends OAuth {
  def get = client(Request(Method.Get, s"$root$path$apiKey$oauthParams"))
}

private[tumblr] trait POST extends OAuth {
  private val request =
    RequestBuilder()
      .url(url)
      .proxied()
      .buildPost(Buf.Utf8(""))

  def post = client(request)
}

private[tumblr] trait DELETE extends OAuth {
  private val request =
    RequestBuilder()
      .url(url)
      .proxied()
      .buildDelete

  def delete = client(request)
}
