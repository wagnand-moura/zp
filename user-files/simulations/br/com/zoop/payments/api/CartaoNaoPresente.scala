package br.com.zoop.payments.api

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object CartaoNaoPresente {
  private val randomCsv = csv("cnp.csv").random

  private val preAuth: HttpRequestBuilder =
    http("CNP Pré Auth")
      .post("/v1/marketplaces/${marketplaceId}/transactions")
      .body(ElFileBody("preAutorizacaoCNP.json"))
      .check(status.is(201))
      .check(jsonPath("$..id").saveAs("transactionId"))

  val preAutorizacaoComVoid: ChainBuilder =
    feed(randomCsv)
      .exec(preAuth)
      .doIf(session => session.contains("transactionId")) {
        exec(http("CNP Pré Auth - Void")
          .post("/v1/marketplaces/${marketplaceId}/transactions/${transactionId}/void")
          .body(ElFileBody("preAutorizacaoCNPVoid.json"))
          .check(status.is(200)))
      }
      .exec(session => session.remove("transactionId"))

  val preAutorizaocaoComCaptura: ChainBuilder =
    feed(randomCsv)
      .exec(preAuth)
      .doIf(session => session.contains("transactionId")) {
        exec(http("CNP Pré Auth - Captura")
          .post("/v1/marketplaces/${marketplaceId}/transactions/${transactionId}/capture")
          .body(ElFileBody("preAutorizacaoCNPCaptura.json"))
          .check(status.is(201)))
      }
      .exec(session => session.remove("transactionId"))

  val oneShot: ChainBuilder =
    feed(randomCsv)
      .exec(http("CNP oneshot")
        .post("/v1/marketplaces/${marketplaceId}/transactions")
        .body(ElFileBody("oneShot.json"))
        .check(status.is(201)))
}
