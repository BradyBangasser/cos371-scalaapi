name := """play-scala-seed"""
organization := "dev.bangasser"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
lazy val akkaVersion = "2.9.2"

scalaVersion := "3.4.0"

resolvers += "Akka library repository".at("https://repo.akka.io/maven")

libraryDependencies ++= Seq(  
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream-typed" % akkaVersion,
)

libraryDependencies += guice 

val AkkaHttpVersion = "10.6.1"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-core" % AkkaHttpVersion,
//   "com.typesafe.akka" %% "akka-http-jackson" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test,
  "com.typesafe.akka" %% "akka-http-xml" % AkkaHttpVersion
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "dev.bangasser.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "dev.bangasser.binders._"
