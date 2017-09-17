package io.github.petesta.tumblr

import com.twitter.finagle.http.{Method, Request}

final case class Info(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val noOauthConfig: NoOauthConfig) extends ApiKey {
  val path = s"${versionBlog(blogName)}/info"
}

final case class Likes(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val noOauthConfig: NoOauthConfig) extends ApiKey {
  val path = s"${versionBlog(blogName)}/likes"
}

final case class Posts(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val noOauthConfig: NoOauthConfig) extends ApiKey {
  val path = s"${versionBlog(blogName)}/posts"
}

final case class Tagged(
  tag: String,
  params: Option[Map[String, String]] = None
)(implicit val noOauthConfig: NoOauthConfig) extends ApiKey {
  val path = "/v2/tagged"

  override def get = client(Request(Method.Get, s"$root$path$apiKey&tag=$tag$keyValuePairs"))
}
