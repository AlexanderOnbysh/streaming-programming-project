import java.util.Properties
import java.util.concurrent._

import akka.actor.ActorSystem
import akka.stream.{Materializer, SystemMaterializer}
import com.typesafe.scalalogging.LazyLogging
import iex.StockIEXClient
import org.apache.kafka.clients.producer._
import play.api.libs.json.Json
import play.api.libs.ws.ahc.StandaloneAhcWSClient
import play.shaded.ahc.org.asynchttpclient.DefaultAsyncHttpClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


object StockProducer extends App with LazyLogging {

  val configuration = new Configuration()

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = SystemMaterializer(system).materializer

  val props = new Properties()

  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.bootstrap)
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String, String](props)
  val topic = configuration.outputTopic
  logger.info("Kafka producer initialized")

  val asyncHttpClient = new DefaultAsyncHttpClient()
  val wsClient = new StandaloneAhcWSClient(asyncHttpClient)

  val client = StockIEXClient(wsClient, token = configuration.token)


  val ex = new ScheduledThreadPoolExecutor(10)

  for (symbol <- configuration.symbols) {
    val f = ex.scheduleAtFixedRate(
      new Runnable {
        override def run(): Unit = client.getPrice(symbol).onComplete {
          case Success(price) => producer.send(
            new ProducerRecord(topic, price.symbol, Json.toJson(price).toString())
          )
          case Failure(e) => logger.error(e.toString)
        }

      }
      , 1, 1, TimeUnit.SECONDS)
  }
}