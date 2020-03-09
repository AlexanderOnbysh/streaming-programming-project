package iex

import play.api.libs.json.{Json, Reads}
import play.api.libs.ws.StandaloneWSClient

import scala.concurrent.{ExecutionContext, Future}

trait IEXClient {

  protected def wsClient: StandaloneWSClient

  protected def token: String

  protected def baseUrl: String = "https://cloud.iexapis.com/stable/stock/"

  protected def wsClientGetRequest[T](
                                       endpoint: String,
                                       queryParameters: Seq[(String, String)] = Seq.empty
                                     )(implicit reads: Reads[T], executionContext: ExecutionContext): Future[T] = wsClient
    .url(baseUrl + endpoint)
    .withQueryStringParameters(queryParameters ++ Seq(("token", token)): _*)
    .get()
    .map(response => {
      println(response.uri)
      println(response.body)
      Json.parse(response.body).as[T]
    })
}