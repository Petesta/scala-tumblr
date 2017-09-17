name := "scala-tumblr"

scalaVersion := "2.11.11"

resolvers += Resolver.sonatypeRepo("public")

val twitter = "com.twitter"
val twitterVersion = "7.1.0"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  twitter %% "finagle-http" % twitterVersion,
  twitter %% "util-collection" % twitterVersion
)

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-value-discard",
  "-encoding", "UTF-8",
  "-feature",
  "-deprecation",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked"
)
