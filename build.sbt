ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "com.udemy"

lazy val hello = (project in file("."))
  .settings(
    name := "Application",

    libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5",

    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
    
  )

