package iex

import iex.Price
import play.api.libs.ws.StandaloneWSClient

import scala.concurrent.{ExecutionContext, Future}

case class StockIEXClient(override protected val wsClient: StandaloneWSClient, override protected val token: String)(
  implicit val executionContext: ExecutionContext) extends IEXClient {

  def getPrice(symbol: String): Future[Price] = wsClientGetRequest[Price](symbol + "/quote")

}