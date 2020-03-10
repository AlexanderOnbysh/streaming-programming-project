name := "combiner-service"
version := "0.1"
scalaVersion := "2.12.1"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.3.1"
libraryDependencies += "org.apache.kafka" % "kafka-streams" % "2.3.1"
libraryDependencies += "org.apache.kafka" %% "kafka-streams-scala" % "2.4.0"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.29"
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.29"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

resolvers += Resolver.bintrayRepo("ovotech", "maven")

libraryDependencies ++= {
  val kafkaSerializationV = "0.1.23" // see the Maven badge above for the latest version
  Seq(
    "com.ovoenergy" %% "kafka-serialization-core" % kafkaSerializationV,
    "com.ovoenergy" %% "kafka-serialization-circe" % kafkaSerializationV, // To provide Circe JSON support
  )
}
val circeVersion = "0.12.3"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case PathList("reference.conf") => MergeStrategy.concat
  case x => MergeStrategy.first
}

assemblyOutputPath in assembly := file("service.jar")

