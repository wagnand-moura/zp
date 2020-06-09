package br.com.zoop.gsp

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder

object Transfer {
  private val csvGsp = csv("/home/wagner.moura/stresstest/user-files/data/gsp.csv").random

  class Brand(val name: String, val url: String, val body: String)

  private val global = new Brand(
    "GSP Transfer",
    s"/v1/marketplaces/${id_marketplace}/sellers/${id_seller}/transfers?date_range%5Bgte%5D=1586736000&date_range%5Blte%5D=1586822399&order_by=transferDate",
    "")

  private val headers = Map(
      "authority" -> "api.staging.pagzoop.com",
      "accept" -> "application/json",
      "Authorization" -> "Basic enBrX3Byb2RfTzZXbzdrUnpZWDExVTBLUUN5NVlsOWh4Og="
  )

  private def transfer(brand: Brand): ChainBuilder =
    exec(http(s"${brand.name}")
      .get(brand.url)
      .headers(headers)
      .check(status.in(200)))

  val gspTransfer: ChainBuilder = feed(csvGsp).exec(transfer(global))
}