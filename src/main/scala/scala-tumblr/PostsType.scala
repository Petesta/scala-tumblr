package io.github.petesta.tumblr

sealed trait PostType {
  private val filtered = "()".toSet

  def `type` = "&type=" + toString.toLowerCase().filterNot(filtered)
}

final case class Text() extends PostType
final case class Quote() extends PostType
final case class Link() extends PostType
final case class Answer() extends PostType
final case class Video() extends PostType
final case class Audio() extends PostType
final case class Photo() extends PostType
final case class Chat() extends PostType
