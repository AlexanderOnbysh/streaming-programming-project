/**
 * Created by Alexander Onbysh 06.12.2019
 */

import java.time.LocalDateTime
import java.util.Properties

import com.danielasfregola.twitter4s.TwitterStreamingClient
import com.danielasfregola.twitter4s.entities.streaming.StreamingMessage
import com.danielasfregola.twitter4s.entities.{AccessToken, ConsumerToken, Tweet}
import com.typesafe.scalalogging.LazyLogging
import org.apache.kafka.clients.producer._

class CompletionCallback extends Callback {
  override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit =
    println(s"Complete ${metadata.timestamp()}")
}

object KafkaProducer extends App with LazyLogging {
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val TOPIC = "t2"
  logger.info("Kafka producer initialized")

  val consumerToken = ConsumerToken(key = "my-consumer-key", secret = "my-consumer-secret")
  val accessToken = AccessToken(key = "my-access-key", secret = "my-access-secret")

  val client = TwitterStreamingClient(consumerToken, accessToken)
  logger.info("Twitter client initialized")

//  def printTweetText: PartialFunction[StreamingMessage, Unit] = {
//    case tweet: Tweet => println(tweet.text)
//  }
//
//  client.sampleStatuses(stall_warnings = true)(printTweetText)

    producer.send(new ProducerRecord(TOPIC, "key", s"Start: ${LocalDateTime.now()}"))
    for (_ <- 1 to 10)
      producer.send(new ProducerRecord(TOPIC, "key", s"Message ${LocalDateTime.now()}"))
    producer.send(new ProducerRecord(TOPIC, "key", s"End: ${LocalDateTime.now()}"), new CompletionCallback())

    producer.close()
}