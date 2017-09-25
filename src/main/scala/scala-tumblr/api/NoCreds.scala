package io.github.petesta.tumblr

final case class Avatar(blogName: String, size: Option[Int] = None) extends Tumblr {
  val path = s"${versionBlog(blogName)}/avatar"
  protected val url = s"$root$path${size.map("?size=" + _.toString).getOrElse("")}"
}
