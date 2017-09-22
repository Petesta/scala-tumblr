package io.github.petesta.tumblr

import com.twitter.finagle.http.{Method, Request}

final case class Avatar(blogName: String, size: Int) extends Tumblr {
  val path = s"${versionBlog(blogName)}/avatar"
  protected val url = s"$root$path?size=${size.toString}"
}
