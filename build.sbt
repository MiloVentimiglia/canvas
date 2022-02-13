import DependencyVersions._
import pl.project13.scala.sbt.JmhPlugin

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.8"
ThisBuild / isSnapshot := true


lazy val root = (project in file("."))
  .settings(
    name := "canvas",
    assembly / mainClass := Some("core.Boot"),
    assembly / assemblyJarName := "canvas.jar",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % scalatestVersion,
      "org.scalatest" %% "scalatest-featurespec" % scalatestFeatureVersion,
      "ch.qos.logback" % "logback-classic" % logbackVersion,
      "org.slf4j" % "log4j-over-slf4j" % slf4jVersion
    )
  )
  .enablePlugins(JmhPlugin)
  .enablePlugins(JavaAppPackaging)
