import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play2-crash-plugin"
  val appVersion      = "0.2.0"

  val appDependencies = Seq(
    "org.crsh" % "crsh.shell.core" % "1.2+",
    "org.slf4j" % "slf4j-jdk14" % "1.+" % "test"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings()

}
