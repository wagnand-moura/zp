package br.com.zoop.payments.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Autenticacao {
  val requestNovo = exec(http("Autenticacao")
    .get("/default/marketplaces/0162893710a6495e86542eeff192baa2/users/mock")
    .header("Authorization", "Basic enBrX3Byb2RfR0RLOU5DbHZ6MU5hRUdBcVZiZEJyTkNSOg=="))

  val requestAtualProd = exec(http("Autenticacao Prod")
    .get("/default/marketplaces/0162893710a6495e86542eeff192baa1/users/mock ")
    .header("Authorization", "Basic enBrX3Byb2RfTzZXbzdrUnpZWDExVTBLUUN5NVlsOWh4Og=="))

  val transacional = exec(http("Transacional")
  .post("/v1/marketplaces/0162893710a6495e86542eeff192baa1/transactions")
  .header("Authorization", "Basic enBrX3Byb2RfZ1c4MEplU05RRXMzQ2JmcW13YkpzZHN0Og==")
  .header("Content-Type", "application/json")
  .body(StringBody("""{
            "amount": 100,
            "currency": "BRL",
            "payment_type": "credit",
            "description": "authorization stress test",
            "statement_descriptor": "@Ingresse.com",
            "on_behalf_of": "0184f9c9501c481eafcf414a2d7eec46",
            "source": {
                "amount": 100,
                "usage": "single_use",
                "currency": "BRL",
                "type": "card",
                "card": {
                    "holder_name": "ZOOP SQUAD QA",
                    "expiration_month": "03",
                    "expiration_year": "2030",
                    "security_code": "737",
                    "card_number": "4444333322221111"
                }
            }
        }""")))
}