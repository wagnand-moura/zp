package br.com.zoop.payments.api

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object CartaoPresente {
  private val randomCsvCielo = csv("/home/wagner.moura/stresstest/user-files/data/cielo.csv").random

  private val randomCsvGlobal = csv("/home/wagner.moura/stresstest/user-files/data/global.csv").random

  class Brand(val name: String, val url: String, val body: String)

  private val cielo = new Brand(
    "Cielo",
    "/v2/card-present/marketplaces/${marketplaceId}/sellers/${sellerId}/transactions",
    "/home/wagner.moura/stresstest/user-files/bodies/transactionCPCielo.json")

  private val global = new Brand(
    "Global",
    "/v1.1/card-present/marketplaces/${marketplaceId}/sellers/${sellerId}/transactions",
    "/home/wagner.moura/stresstest/user-files/bodies/transactionCPGlobal.json")

  private def transaction(brand: Brand): HttpRequestBuilder =
    http(s"CP Create ${brand.name}")
      .post(brand.url)
      .body(ElFileBody(brand.body))
      .check(status.is(201))
      .check(jsonPath("$..id").saveAs("transactionId"))

  private def comReversa(brand: Brand): ChainBuilder =
    exec(transaction(brand))
      .exitHereIfFailed
      .exec(http(s"CP Reverse ${brand.name}")
        .post("/v1/card-present/marketplaces/${marketplaceId}/transactions/${transactionId}/reversals")
        .body(ElFileBody("/home/wagner.moura/stresstest/user-files/bodies/transactionCPReversal.json"))
        .check(status.is(200)))

  private def comConfirmacao(brand: Brand): ChainBuilder =
    exec(transaction(brand))
      .exitHereIfFailed
      .exec(http(s"CP Confirm ${brand.name}")
        .put("/v1.1/card-present/marketplaces/${marketplaceId}/transactions/${transactionId}")
        .body(ElFileBody("/home/wagner.moura/stresstest/user-files/bodies/transactionCPConfirm.json"))
        .check(status.is(200)))

  val comReversaCielo: ChainBuilder = feed(randomCsvCielo).exec(comReversa(cielo))

  val comReversaGlobal: ChainBuilder = feed(randomCsvGlobal).exec(comReversa(global))

  val comConfirmacaoCielo: ChainBuilder = feed(randomCsvCielo).exec(comConfirmacao(cielo))

  val comConfirmacaoGlobal: ChainBuilder = feed(randomCsvGlobal).exec(comConfirmacao(global))
}