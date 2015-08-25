import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "quora"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
     "org.postgresql" % "postgresql" % "9.3-1103-jdbc3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here      
    )

}
