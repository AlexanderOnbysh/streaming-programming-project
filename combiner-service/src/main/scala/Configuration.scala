import java.util

class Configuration {
  val env: util.Map[String, String] = System.getenv()

  val bootstrap: String = env.getOrDefault("BOOTSTRAP_URL", "localhost:9092")
  val stocksTopic: String = env.getOrDefault("STOCKS_TOPIC", "stocks")
  val tweetsTopic: String = env.getOrDefault("TWEETS_TOPIC", "tweets")
  val outputTopic: String = env.getOrDefault("OUTPUT_TOPIC", "combined")
}

