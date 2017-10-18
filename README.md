scala-tumblr
============

## Intro
`scala-tumblr` is a library built on top of `Finagle` to interface with [Tumblr's API].

## How to use
```scala
libraryDependencies += "io.github.petesta" %% "scala-tumblr" % "0.1.2"
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

implicit val apiConfig = ApiConfig("apiKey")

// NOTE:
//   Posts(
//     blogName: String,
//     params: Option[Map[String, String]] = None
//   )(implicit apiConfig: ApiConfig) extends ApiKey
Posts("blogName").get // Future[Response]

implicit val oauthConfig = OauthConfig("oauthToken", "oauthTokenSecret")

// NOTE:
//   final case class Following(
//     blogName: String,
//     params: Option[Map[String, String]] = None
//   )(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth
Following("blogName").get // Future[Response]
```

## Note

Finagle returns Twitter Futures, not Scala Futures. Here is a snippet if you'd like
to convert from a Twitter Future to a Scala Future.

```scala
import com.twitter.util.{Future => TwitterF}
import scala.concurrent.{Await, Future => ScalaF, Promise => ScalaP}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object FutureConverter {
  def twitterToScala[T](twitterF: TwitterF[T]): ScalaF[T] = {
    val scalaP = ScalaP[T]
    twitterF.onSuccess { r: T =>
      scalaP.success(r)
      ()
    }
    twitterF.onFailure { e: Throwable =>
      scalaP.failure(e)
      ()
    }
    scalaP.future
  }
}
```

[Tumblr's API]: https://www.tumblr.com/docs/en/api/v2
