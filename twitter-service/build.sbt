name := "twitter-service"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.3.1"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.29"
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.29"
libraryDependencies += "com.danielasfregola" %% "twitter4s" % "6.2"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

