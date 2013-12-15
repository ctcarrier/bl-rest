import com.typesafe.sbt.SbtStartScript
import spray.revolver.RevolverPlugin.Revolver

organization := "simplyoverkill"

name := "blrest"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.10.3"

seq(Revolver.settings: _*)

seq(SbtStartScript.startScriptForClassesSettings: _*)

javaOptions in Revolver.reStart += "-Dakka.mode=dev"

javaOptions in Revolver.reStart += "-Xdebug"

unmanagedResourceDirectories in Compile <+=
    (baseDirectory) { _ / "src" / "main" / "webapp" }

ivyXML :=
 	        <dependencies>
 	        	<exclude org="org.slf4j" module="slf4j-simple"/>
	 	        <exclude org="commons-logging" module="commons-logging"/>
                <exclude org="org.slf4j" module="slf4j-jcl"/>
 	        </dependencies>


ivyScala ~= { _.map(_.copy(checkExplicit = false)) }

libraryDependencies ++= Seq(
  //LOGGING
    "org.slf4j" % "jcl-over-slf4j" % "1.7.2",
    "org.slf4j" % "slf4j-api" % "1.7.2",
    "com.typesafe" %% "scalalogging-slf4j" % "1.0.1",
  "ch.qos.logback" % "logback-classic" % "0.9.28" % "runtime",
  "ch.qos.logback" % "logback-core" % "0.9.28" % "runtime",
    //SPRAY
    "io.spray" % "spray-routing" % "1.2.0" % "compile" withSources(),
    "io.spray" % "spray-http" % "1.2.0" % "compile" withSources(),
    "io.spray" % "spray-can" % "1.2.0" % "compile" withSources(),
    "io.spray" % "spray-io" % "1.2.0" % "compile" withSources(),
    "io.spray" % "spray-caching" % "1.2.0" % "compile" withSources(),
    //AKKA
    "com.typesafe.akka" %% "akka-actor" % "2.2.3",
  "com.typesafe.akka" %% "akka-slf4j" % "2.2.3",
    //Jackson
  "org.json4s" %% "json4s-jackson" % "3.2.6",
  //TESTING
  "org.specs2" %% "specs2" % "2.3.6" % "test" exclude("com.chuusai", "shapeless_2.10.3"),
  //ReactiveMongo
  "org.reactivemongo" %% "reactivemongo" % "0.10.0-SNAPSHOT" % "compile"
)

resolvers ++= Seq(
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  "Sonatype OSS Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "Spray repo" at "http://repo.spray.cc",
  "Local Maven Repository" at "file://" + Path.userHome + "/.m2/repository"
)

scalacOptions in Test ++= Seq("-Yrangepos")

testOptions in Test += Tests.Setup( loader => {
  val oldEnv = System.getProperty("akka.mode")
  if (oldEnv != null) {
    System.setProperty("akka.mode.old", oldEnv)
  }
  System.setProperty("akka.mode", "test")
} )

testOptions in Test += Tests.Cleanup( loader => {
  val oldEnv = System.getProperty("akka.mode.old")
  if (oldEnv != null) {
    System.setProperty("akka.mode", oldEnv)
  }
} )
