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
}