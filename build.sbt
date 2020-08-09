ThisBuild / organization := "garook"
ThisBuild / scalaVersion := "2.13.3"

ThisBuild / description := "Library to process xml elements one by one"
ThisBuild / licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))

lazy val root = (project in file("."))
  .enablePlugins(GitVersioning)
  .aggregate(xml)
  .settings(
    name := "garook-root"
  )

lazy val xml = (project in file("xml"))
  .settings(
    name := "garook-xml",
    libraryDependencies ++= Seq(
      "com.fasterxml" % "aalto-xml" % "1.2.1",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "ch.qos.logback" % "logback-classic" % "1.2.3" % Test,
      "org.scalatest" %% "scalatest" % "3.2.0" % "test",
      "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1" % "test"
    )
  )

