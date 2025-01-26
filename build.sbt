ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.4"

lazy val root = (project in file("."))
  .settings(
      name := "CaskUpickleExample"
  )

lazy val caskVersion = "0.10.2"
lazy val upickleVersion = "4.1.0"

libraryDependencies ++= Seq(
  "com.lihaoyi" %% "cask" % caskVersion,
  "com.lihaoyi" %% "upickle" % upickleVersion
)
