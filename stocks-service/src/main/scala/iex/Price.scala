package iex

import play.api.libs.json.{Format, Json}

case class Price(symbol: String, companyName: String, latestPrice: Double, previousClose: Double)

object Price {

  implicit val format: Format[Price] = Json.format[Price]
}