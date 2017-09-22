package io.github.petesta.tumblr

sealed trait PostType {
  val params: Option[Map[String, String]]

  override def toString = this.getClass.getSimpleName.toLowerCase()
}

final case class Text(body: String, title: Option[String] = None) extends PostType {
  val params = Some(
    List(
      Some(("body" -> body)),
      title.map(("title" -> _))
    ).flatten.toMap
  )
}

final case class Quote(quote: String, source: Option[String] = None) extends PostType {
  val params = Some(
    List(
      Some(("quote" -> quote)),
      source.map(("source" -> _))
    ).flatten.toMap
  )
}

final case class Link(
  url: String,
  title: Option[String] = None,
  description: Option[String] = None,
  thumbnail: Option[String] = None,
  excerpt: Option[String] = None,
  author: Option[String] = None
) extends PostType {
  val params = Some(
    List(
      Some(("url" -> url)),
      title.map(("title" -> _)),
      description.map(("description" -> _)),
      thumbnail.map(("thumbnail" -> _)),
      excerpt.map(("excerpt" -> _)),
      author.map(("author" -> _))
    ).flatten.toMap
  )
}

sealed abstract case class Video(
  embed: Option[String],
  data: Option[String] = None,
  caption: Option[String] = None
) extends PostType {
  val params = Some(
    List(
      embed.map(("embed" -> _)),
      data.map(("data" -> _)),
      caption.map(("caption" -> _))
    ).flatten.toMap
  )
}

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
) extends PostType {
  val params = Some(
    List(
      externalUrl.map(("external_url" -> _)),
      data.map(("data" -> _)),
      caption.map(("caption" -> _))
    ).flatten.toMap
  )
}

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
) extends PostType {
  val params = Some(
    List(
      source.map(("source" -> _)),
      data.map(("data" -> _.mkString)),
      data64.map(("data64" -> _))
    ).flatten.toMap
  )
}

object Photo {
  def apply(source: Option[String], data: Option[List[String]] = None, data64: Option[String] = None): Option[Photo] =
    if (List(source, data, data64).map(_.isDefined).filter(_ == true).length == 1)
      Some(new Photo(source, data, data64) {})
    else
      None
}

final case class Chat(conversation: String, title: Option[String] = None) extends PostType {
  val params = Some(
    List(
      Some("conversation" -> conversation),
      title.map(("title" -> _))
    ).flatten.toMap
  )
}
