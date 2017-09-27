name := "scala-tumblr"

organization := "io.github.petesta"

version := "0.1.1"

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
val yWarnValueDiscard = "-Ywarn-value-discard"

scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  yWarnUnusedImport,
  yWarnValueDiscard,
  "-encoding", "UTF-8",
  "-feature",
  "-deprecation",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked"
)

scalacOptions in (Compile, console) ~= { _.filterNot(Set(yWarnUnusedImport, yWarnValueDiscard)) }

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra in Global := {
  <url>https://github.com/Petesta/scala-tumblr</url>
  <licenses>
    <license>
      <name>MIT License</name>
      <url>http://www.opensource.org/licenses/mit-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
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
