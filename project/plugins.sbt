//resolvers ++= Resolver.sonatypeOssRepos("https://github.com/scoverage/sbt-scoverage")
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.2.2")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.3.1")

//addSbtPlugin("com.lihaoyi" % "sbt-ammonite" % "2.5.0")

