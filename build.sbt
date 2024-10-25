ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.1"

lazy val root = (project in file("."))
  .settings(
    name := "Roobert"
  )

libraryDependencies ++= Seq(
  "org.scalameta" %% "munit" % "1.0.0" % Test,
  "org.scalactic" %% "scalactic" % "3.2.19",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
)