name := "ds-migrator"

version := "0.0.1"

scalaVersion := "2.11.2"

resolvers += "Apache HBase" at "https://repository.apache.org/content/repositories/releases"

resolvers += "Thrift" at "http://people.apache.org/~rawson/repo/"

val scalazVersion = "7.1.0"

val hadoopVersion = "1.2.1"

val hbaseVersion = "0.94.15"

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-effect" % scalazVersion,
  "org.scalaz" %% "scalaz-concurrent" % scalazVersion,
  "org.apache.hadoop" % "hadoop-client" % hadoopVersion,
  "org.apache.hbase" % "hbase" % hbaseVersion,
  "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test"
)

scalacOptions += "-feature"

scalacOptions += "-unchecked"

scalacOptions += "-Ywarn-value-discard"

initialCommands in console := "import scalaz._, Scalaz._, dsmigrator.core._"

initialCommands in console in Test := "import scalaz._, Scalaz._, scalacheck.ScalazProperties._, scalacheck.ScalazArbitrary._,scalacheck.ScalaCheckBinding._, dsmigrator.core._"

testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "500", "-minSuccessfulTests", "200", "-workers", "4", "-verbosity", "1")

fork in Test := true

instrumentSettings

ScoverageKeys.minimumCoverage := 70

ScoverageKeys.failOnMinimumCoverage := true

seq(jrebelSettings: _*)
