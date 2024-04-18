name := """cos371-scalaapi"""
organization := "dev.bangasser"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
lazy val akkaVersion = "2.9.2"
lazy val PekkoHttpVersion = "1.0.1"

scalaVersion := "3.4.0"

libraryDependencies += guice 

val AkkaHttpVersion = "10.6.1"
libraryDependencies ++= Seq(
  "org.apache.pekko" %% "pekko-http-spray-json" % PekkoHttpVersion,
  "io.spray" %%  "spray-json" % "1.3.6"
)