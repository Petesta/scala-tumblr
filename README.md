scala-tumblr
============

## Intro
`scala-tumblr` is a library built on top of `Finagle` to interface with Tumblr's API.

## How to use
```scala
libraryDependencies += "io.github.petesta" %% "scala-tumblr" % "0.1"
```

## Endpoints
There are three different types of endpoints.
* Don't need `api_key` and `oauth_token` and `oauth_token_secret`
* Need `api_key` and don't need `oauth_token` and `oauth_token_secret`
* Need `api_key` and need `oauth_token` and `oauth_token_secret`

## Examples
```scala
import io.github.petesta.tumblr._

// NOTE: There is only one endpoint that doesn't need an implicit config.
// Avatar(blogName: String, size: Int)
Avatar("blogName", 64).get // Future[Response]

implicit val noOauthConfig = NoOauthConfig("apiKey")

// NOTE:
//   Posts(
//     blogName: String,
//     params: Option[Map[String, String]] = None
//   )(implicit noOauthConfig: NoOauthConfig) extends ApiKey
Posts("blogName").get // Future[Response]

implicit val oauthConfig = OauthConfig("oauthToken", "oauthTokenSecret")

// NOTE:
//   final case class Following(
//     blogName: String,
//     params: Option[Map[String, String]] = None
//   )(implicit val oauthConfig: OauthConfig, val noOauthConfig: NoOauthConfig) extends OAuth
```
