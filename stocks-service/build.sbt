name := "stocks-service"

version := "0.1"

scalaVersion := "2.12.1"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.3.1"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.29"
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.29"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "2.1.2"
libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "2.1.2"

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}
assemblyOutputPath in assembly := file("service.jar")

