ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "Roobert",
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "1.0.2" % Test,
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.0", // Ersetzen Sie mit der neuesten Version, falls erforderlich
      "com.fasterxml.jackson.core" % "jackson-core" % "2.13.0",
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.13.0",
      "io.circe" %% "circe-core" % "0.14.10",
      "io.circe" %% "circe-generic" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10"
    )
  )

