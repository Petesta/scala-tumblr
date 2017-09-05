package io.github.petesta.tumblr

sealed trait PostType {
  override def toString = this.getClass.getSimpleName.toLowerCase()

  def `type` = s"&text=$toString"
}

final case class Text(body: String, title: Option[String] = None) extends PostType

final case class Quote(quote: String, source: Option[String] = None) extends PostType

final case class Link(
  url: String,
  title: Option[String] = None,
  description: Option[String] = None,
  thumbnail: Option[String] = None,
  excerpt: Option[String] = None,
  author: Option[String] = None
) extends PostType

sealed abstract case class Video(
  embed: Option[String],
  data: Option[String] = None,
  caption: Option[String] = None
) extends PostType

object Video {
  def apply(embed: Option[String], data: Option[String] = None, caption: Option[String] = None): Option[Video] =
    if (List(embed, data).map(_.isDefined).filter(_ == true) == 1)
      Some(new Video(embed, data, caption) {})
    else
      None
}

sealed abstract case class Audio(
  externalUrl: Option[String],
  data: Option[String] = None,
  caption: Option[String] = None
) extends PostType

object Audio {
  def apply(externalUrl: Option[String], data: Option[String] = None, caption: Option[String] = None): Option[Audio] =
    if (List(externalUrl, data).map(_.isDefined).filter(_ == true) == 1)
      Some(new Audio(externalUrl, data, caption) {})
    else
      None
}

sealed abstract case class Photo(
  source: Option[String],
  data: Option[List[String]] = None,
  data64: Option[String] = None
) extends PostType

object Photo {
  def apply(source: Option[String], data: Option[List[String]] = None, data64: Option[String] = None): Option[Photo] =
    if (List(source, data, data64).map(_.isDefined).filter(_ == true).length == 1)
      Some(new Photo(source, data, data64) {})
    else
      None
}

final case class Chat(conversation: String, title: Option[String] = None) extends PostType
