name := "scala-tumblr"

scalaVersion := "2.11.11"

description := "Tumblr API"

val twitter = "com.twitter"
val twitterVersion = "7.1.0"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  twitter %% "finagle-http" % twitterVersion,
  twitter %% "util-collection" % twitterVersion
)

val yWarnUnusedImport = "-Ywarn-unused-import"

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  yWarnUnusedImport,
  "-Ywarn-value-discard",
  "-encoding", "UTF-8",
  "-feature",
  "-deprecation",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked"
)

scalacOptions in (Compile, console) ~= { _.filterNot(Set(yWarnUnusedImport)) }

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := Function.const(false)

pomExtra in Global := {
  <url>https://github.com/Petesta/scala-tumblr</url>
  <scm>
    <url>git@github.com:Petesta/scala-tumblr.git</url>
    <connection>scm:git:git@github.com:Petesta/scala-tumblr.git</connection>
  </scm>
  <developers>
    <developer>
      <id>Petesta</id>
      <name>Pete Cruz</name>
      <url>https://github.com/Petesta/</url>
    </developer>
  </developers>
}
