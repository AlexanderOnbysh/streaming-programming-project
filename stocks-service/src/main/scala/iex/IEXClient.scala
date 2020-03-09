package perretta.iex.client

import play.api.libs.json.{Json, Reads}
import play.api.libs.ws.StandaloneWSClient

import scala.concurrent.{ExecutionContext, Future}

trait IEXClient {

  protected def wsClient: StandaloneWSClient

  protected def baseUrl: String

  protected def wsClientGetRequest[T](
                                       endpoint: String,
                                       queryParameters: Seq[(String, String)] = Seq.empty
                                     )(implicit reads: Reads[T], executionContext: ExecutionContext): Future[T] = wsClient
    .url(baseUrl + endpoint)
    .withQueryStringParameters(queryParameters: _*)
    .get()
    .map(response => Json.parse(response.body).as[T])

}