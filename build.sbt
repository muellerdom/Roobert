ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.5.1"

lazy val root = (project in file("."))
  .settings(
    name := "Roobert",
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    )
  )
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.0" // Ersetzen Sie mit der neuesten Version, falls erforderlich
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.13.0"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.13.0"


