package io.github.petesta.tumblr

import com.twitter.finagle.Http
import com.twitter.finagle.http.{Method, Request, RequestBuilder, Response}
import com.twitter.io.Buf
import com.twitter.util.Future

private[tumblr] trait Tumblr {
  val path: String

  protected val url: String
  protected val root = "api.tumblr.com"
  protected val client = Http.client.withTls(root).newService(s"$root:443")

  protected def paramsString(p: Option[Map[String, String]]) =
    p.map { opt =>
      opt.view.map { case (key, value) => key + "=" + value }.mkString("&", "&", "")
    }.getOrElse("")

  protected def versionBlog(blogName: String) = s"/v2/blog/$blogName.tumblr.com"
}

private[tumblr] trait ApiKey extends Tumblr {
  implicit val apiConfig: ApiConfig

  val params: Option[Map[String, String]]

  protected val apiKey = s"?api_key=${apiConfig.apiKey}"
  protected val url = s"$root$path$apiKey${paramsString(params)}"

  def get = client(Request(Method.Get, url))
}

private[tumblr] trait OAuth extends Tumblr {
  implicit val oauthConfig: OauthConfig

  val params: Option[Map[String, String]]

  protected val apiKey = s"?api_key=${oauthConfig.apiConfig.apiKey}"

  protected val oauthParams =
    s"&oauth_token=${oauthConfig.oauthToken}&oauth_token_secret=${oauthConfig.oauthTokenSecret}${paramsString(params)}"

  protected val url = s"https://$root$path$apiKey$oauthParams${paramsString(params)}"
}

private[tumblr] trait GET extends OAuth {
  def get = client(Request(Method.Get, url))
}

private[tumblr] trait POST extends OAuth {
  val bodyParams: Option[Map[String, String]]

  override protected val url = s"https://$root$path$apiKey$oauthParams"

  private val request =
    RequestBuilder()
      .url(url)
      .proxied()
      .buildPost(Buf.Utf8(paramsString(bodyParams)))

  def post = client(request)
}

private[tumblr] trait DELETE extends OAuth {
  val params = None

  private val request =
    RequestBuilder()
      .url(url)
      .proxied()
      .buildDelete

  def delete = client(request)
}
