import java.util

class Configuration {
  val env: util.Map[String, String] = System.getenv()

  val bootstrap: String = env.getOrDefault("BOOTSTRAP_URL", "localhost:9092")
  val outputTopic: String = env.getOrDefault("OUTPUT_TOPIC", "test2")
  //    creds
  val symbols: Seq[String] = env.getOrDefault("SYMBOLS", "msft").split(",").toSeq
  val token: String = env.get("TOKEN")


}

