import java.util

class Configuration {
  val env: util.Map[String, String] = System.getenv()

  val bootstrap: String = env.getOrDefault("BOOTSTRAP_URL", "localhost:9092")
  val outputTopic: String = env.getOrDefault("OUTPUT_TOPIC", "test")
  //    creds

}

