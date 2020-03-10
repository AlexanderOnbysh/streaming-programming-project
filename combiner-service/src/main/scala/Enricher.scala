
import java.util.Properties

import io.circe.generic.auto._
import model.{CirceSerdes, EnrichedTweets, Price, Tweet}
import org.apache.kafka.clients.consumer._
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.scala.kstream.{Consumed, Joined, KStream, Produced}
import org.apache.kafka.streams.scala.{Serdes, StreamsBuilder}
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig, Topology}


object Enricher extends App {
  val configuration = new Configuration()

  def joinStreams: Topology = {

    val builder = new StreamsBuilder

    implicit val stringKey: Serde[String] = CirceSerdes.serde[String]
    implicit val tweetValue: Serde[Tweet] = CirceSerdes.serde[Tweet]
    val tweet = builder.table[String, Tweet](configuration.tweetsTopic)(Consumed.`with`[String, Tweet])

    implicit val priceValue: Serde[Price] = CirceSerdes.serde[Price]
    val price = builder.stream[String, Price](configuration.stocksTopic)(Consumed.`with`[String, Price])

    val enrichedDataStream: KStream[String, EnrichedTweets] = price.join(tweet)(
      (price: Price, tweet: Tweet) => {
        EnrichedTweets(price = price, tweet = tweet)
      }
    )(Joined.`with`[String, Price, Tweet])

    enrichedDataStream.to(configuration.outputTopic)(
      Produced.`with`[String, EnrichedTweets](Serdes.String, CirceSerdes.serde[EnrichedTweets])
    )
    builder.build()
  }

  val props = new Properties()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test_app")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.bootstrap)
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  props.put("max.poll.records", "100")

  val streams = new KafkaStreams(joinStreams, props)
  streams.cleanUp()
  streams.start()

  sys.ShutdownHookThread {
    streams.close()
  }
}