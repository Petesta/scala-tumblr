package io.github.petesta.tumblr

private[tumblr] trait TumblrConfig

final case class NoOauthConfig(apiKey: String) extends TumblrConfig

final case class OauthConfig(
  oauthToken: String,
  oauthTokenSecret: String
)(implicit val oauthConfig: OauthConfig) extends TumblrConfig
