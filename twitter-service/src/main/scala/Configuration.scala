import java.util

class Configuration {
  val env: util.Map[String, String] = System.getenv()

  val bootstrap: String = env.getOrDefault("BOOTSTRAP_URL", "localhost:9092")
  val outputTopic: String = env.getOrDefault("OUTPUT_TOPIC", "tweets")
  val symbols: List[String] = env.getOrDefault("SYMBOLS", "MSFT").split(",").toList
  //    keys
  val ConsumerKey: String = env.get("CONSUMER_KEY")
  val ConsumerSecret: String = env.get("CONSUMER_SECRET")
  val AccessToken: String = env.get("ACCESS_TOKEN")
  val AccessTokenSecret: String = env.get("ACCESS_TOKEN_SECRET")
}

