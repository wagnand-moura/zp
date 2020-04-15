package br.com.zoop.rules.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._

object Rules {
    private val randomCsvMotor = csv("motorRegras.csv").queue

    class Brand(val name: String, val url: String, val body: String)

    private val global = new Brand(
        "Motor Regras",
        "/v1/transaction/fire",
        "motorRegras.json")
    
    private def motorRegras(brand: Brand): ChainBuilder =
        exec(http(s"${brand.name}")
        .post("/v1/transaction/fire")
        .body(ElFileBody(brand.body))
        .check(status.in(200, 422)))

    val motor: ChainBuilder = feed(randomCsvMotor).exec(motorRegras(global))
}