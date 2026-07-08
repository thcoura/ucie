name := "ucie_digital"
ThisBuild / organization := "edu.berkeley.cs"
ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"
ThisBuild / scalacOptions := Seq("-deprecation","-feature","-language:reflectiveCalls","-Xcheckinit","-Xlint")

Compile / doc / scalacOptions += "-groups"

val chiselVersion = "3.6.0"

// rocketchip dependency replaced by vendored util classes in src/main/scala/util/
// Set env var SKIP_TILELINK=1 to exclude TileLink bridge + integration tests
libraryDependencies ++= Seq(
  "edu.berkeley.cs" %% "chisel3" % chiselVersion,
  "edu.berkeley.cs" %% "chiseltest" % "0.6.2" % "test",
  "org.scalatest" %% "scalatest" % "3.2.18" % "test",
)

resolvers ++= Resolver.sonatypeOssRepos("releases")
resolvers += Resolver.mavenLocal

addCompilerPlugin("edu.berkeley.cs" % "chisel3-plugin" % chiselVersion cross CrossVersion.full)

// Optional: exclude TileLink bridge + tests that need Diplomacy
val skipTileLink = sys.env.get("SKIP_TILELINK").contains("1")

def skipFilter(p: String): Boolean =
  (p.contains("/tilelink/") && !p.endsWith("Common.scala")) ||
  p.contains("ProtoFDILBTestHarness") || p.contains("ProtoFDILoopback") ||
  p.contains("FdiLoopbackTest") || p.contains("FdiLoopback.scala") ||
  p.contains("AfeLoopbackTest") || p.contains("AfeLoopback.scala")

Compile / unmanagedSources / excludeFilter := {
  if (skipTileLink) { (f: java.io.File) => skipFilter(f.getPath) }
  else { (f: java.io.File) => false }
}

Test / unmanagedSources / excludeFilter := {
  if (skipTileLink) { (f: java.io.File) => skipFilter(f.getPath) }
  else { (f: java.io.File) => false }
}

import Tests._
Test / fork := true
Test / testGrouping := (Test / testGrouping).value.flatMap { group =>
   group.tests.map { test =>
      Group(test.name, Seq(test), SubProcess(ForkOptions()))
   }
}
concurrentRestrictions := Seq(Tags.limit(Tags.ForkedTestGroup, 72))
