import java.util.Properties

import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.enums.Language.English
import com.danielasfregola.twitter4s.entities.streaming.StreamingMessage
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, Tweet}
import com.typesafe.scalalogging.LazyLogging
import io.circe.generic.auto._
import io.circe.syntax._
import org.apache.kafka.clients.producer._


object TwitterProducer extends App with LazyLogging {
  val configuration = new Configuration()

  val props = new Properties()

  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.bootstrap)
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val topic = configuration.outputTopic
  logger.info("Kafka producer initialized")

  val consumerToken = ConsumerToken(key = configuration.ConsumerKey, secret = configuration.ConsumerSecret)
  val accessToken = AccessToken(key = configuration.AccessToken, secret = configuration.AccessTokenSecret)

  val client = TwitterStreamingClient(consumerToken, accessToken)
  logger.info("Twitter client initialized")


  def tweetToStream: PartialFunction[StreamingMessage, Unit] = {
    case tweet: Tweet =>
      val record = TweetRecord("MSFT", tweet.text, tweet.favorite_count, tweet.favorited)
      logger.debug(record.toString)
      producer.send(
        new ProducerRecord(
          topic,
          "MSFT",
          record.asJson.noSpaces
        )
      )
  }

  client.filterStatuses(
    //    all tweets with Tesla
    tracks = List("Microsoft"),
    //    San Francisco
    languages = List(English)
  )(tweetToStream)}