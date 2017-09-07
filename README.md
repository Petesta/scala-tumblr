scala-tumblr
============

## Intro
`scala-tumblr` is a library built on top of Finagle to interface with Tumblr's API.

## How to use
```scala
libraryDependencies += "io.github.petesta" %% "scala-tumblr" % "0.1"
```

## Examples
```scala
import io.github.petesta.tumblr._

implicit val config = TumblrConfig()
implicit val config = TumblrConfig(Some("apiKey"))
implicit val config = TumblrConfig(Some("apiKey"), Some("oauthToken"), Some("oauthTokenSecret"))

// NOTE: Posts(blogName: String, params: Option[Map[String, String]] = None)
Posts("tumblrBlogName").get // Future[Response]
```
