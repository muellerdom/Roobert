import scala.collection.immutable.Seq

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

// Verbesserte Compiler-Einstellungen für Geschwindigkeit und Fehlertoleranz
scalacOptions ++= Seq(
  "-deprecation", // Warnt vor deprecated Features
  "-feature", // Informiert über nicht standardmäßige Features
  "-unchecked", // Zusätzliche Typ-Prüfungen
)

// Test Coverage aktivieren
coverageEnabled := true

lazy val root = (project in file("."))
  .enablePlugins(JacocoPlugin, AssemblyPlugin)
  .settings(
    assembly / assemblyJarName := {
      val projectVersion = sys.env.getOrElse("VERSION", "").stripPrefix("refs/tags/")
      s"Roobert-${projectVersion}.jar"
    },
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", "substrate", "config", "jniconfig-x86_64-darwin.json") =>
        MergeStrategy.last
      case PathList("META-INF", "substrate", "config", "reflectionconfig-x86_64-darwin.json") =>
        MergeStrategy.last
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case PathList("META-INF", _*) => MergeStrategy.discard
      case PathList("reference.conf") => MergeStrategy.concat
      case _ => MergeStrategy.first
    },
    name := "Roobert",
    libraryDependencies ++= Seq(
      // Testing-Tools
      "org.scalameta" %% "munit" % "1.1.0" % Test,
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "org.mockito" %% "mockito-scala" % "1.17.37" % Test, // Mocking-Framework
      "org.scalamock" %% "scalamock" % "6.1.1" % Test,

      // JSON-Parsing & Verarbeitung
      "io.circe" %% "circe-core" % "0.14.10",
      "io.circe" %% "circe-generic" % "0.14.10",
      "io.circe" %% "circe-parser" % "0.14.10",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.15.2", // Letzte Version
      "com.fasterxml.jackson.core" % "jackson-core" % "2.15.2",
      "com.fasterxml.jackson.core" % "jackson-annotations" % "2.15.2",

      // Dependency Injection
      "com.google.inject" % "guice" % "7.0.0",
      "net.codingwell" %% "scala-guice" % "7.0.0",

      // ScalaFX (UI Unterstützung für JavaFX)
      "org.scalafx" %% "scalafx" % "23.0.1-R34", // Optional: Test auf Versionskompatibilität

      // Scala REPL-Support
      "com.lihaoyi" %% "ammonite" % "3.0.0" cross CrossVersion.full,

      // PowerMock (Legacy Testing-Tools, wenn nötig)
      "org.powermock" % "powermock-module-junit4" % "2.0.9" % Test,
      "org.powermock" % "powermock-api-mockito2" % "2.0.9" % Test,

      "org.jline" % "jline-terminal" % "3.28.0",
      "org.jline" % "jline" % "3.28.0"
    ),
    // Jacoco Integration für Test-Berichterstattung
    jacocoCoverallsServiceName := "github-actions",
    jacocoCoverallsBranch := sys.env.get("CI_BRANCH"),
    jacocoCoverallsPullRequest := sys.env.get("GITHUB_EVENT_NAME"),
    jacocoCoverallsRepoToken := sys.env.get("COVERALLS_REPO_TOKEN")
  )