ThisBuild / organization := "io.github.vistritium"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / description := "Library to process xml elements one by one"
ThisBuild / licenses := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / homepage := Some(url("https://github.com/Vistritium/garook"))
ThisBuild / scmInfo := Some(ScmInfo(url("https://github.com/Vistritium/garook"), "https://github.com/Vistritium/garook.git"))

ThisBuild / developers := List(Developer(
  "Maciej Nowicki",
  "Maciej Nowicki",
  "maciej@nowicki.com",
  url("https://github.com/Vistritium")
))

ThisBuild / publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

lazy val root = (project in file("."))
  .enablePlugins(GitVersioning)
  .aggregate(xml)
  .settings(
    name := "garook-root",
  )

lazy val commonDependenies = Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % Test,
  "org.scalatest" %% "scalatest" % "3.2.0" % "test",
  "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1" % "test"
)

lazy val xml = (project in file("xml"))
  .settings(
    name := "garook-xml",
    libraryDependencies ++= commonDependenies ++ Seq(
      "com.fasterxml" % "aalto-xml" % "1.2.1"
    )
  )


