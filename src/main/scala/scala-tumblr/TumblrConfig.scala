package io.github.petesta.tumblr

final case class ApiConfig(apiKey: String)

final case class OauthConfig(
  oauthToken: String,
  oauthTokenSecret: String
)(implicit val oauthConfig: OauthConfig)
