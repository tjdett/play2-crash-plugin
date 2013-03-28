import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "play2-crash-plugin"
  val appVersion      = "0.1-SNAPSHOT"

  val appDependencies = Seq(
    "org.crsh" % "crsh.shell.core" % "1.2+"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings()

}
