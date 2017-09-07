package io.github.petesta.tumblr

sealed trait TumblrConfig {
  val apiKey: String
}

final case class NoOauthConfig(apiKey: String) extends TumblrConfig

final case class OauthConfig(
  apiKey: String,
  oauthToken: String,
  oauthTokenSecret: String
) extends TumblrConfig
