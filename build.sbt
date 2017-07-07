name := "stream-poc"

version := "1.0"

scalaVersion := "2.11.11"

libraryDependencies := Seq(
  "com.h2database" % "h2" % "1.3.148",
  "com.typesafe.akka" %% "akka-stream" % "2.5.2",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "org.postgresql" % "postgresql" % "9.4.1208",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.2" % Test,
  "org.scalatest" %% "scalatest" % "3.0.1" % Test

)