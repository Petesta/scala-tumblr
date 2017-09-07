name := "scala-tumblr"

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("public")

val twitter = "com.twitter"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  twitter %% "finagle-http" % "7.0.0",
  twitter %% "util-collection" % "6.27.0"
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
