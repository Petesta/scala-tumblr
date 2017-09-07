package io.github.petesta.tumblr

final case class TumblrConfig(
  apiKey: Option[String] = None,
  oauthToken: Option[String] = None,
  oauthTokenSecret: Option[String] = None
)
