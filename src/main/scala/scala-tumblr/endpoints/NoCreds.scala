package io.github.petesta.tumblr

import com.twitter.finagle.http.{Method, Request}

final case class Avatar(blogName: String, size: Int) extends Tumblr {
  val path = s"${versionBlog(blogName)}/avatar"
  val params: Option[Map[String, String]] = None

  def get = client(Request(Method.Get, s"$root$path?size=$size"))
}
