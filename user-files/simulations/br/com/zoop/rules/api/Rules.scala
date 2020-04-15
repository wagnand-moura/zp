package br.com.zoop.rules.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder

object Rules {
    private val randomCsvMotor = csv("/home/wagner.moura/stresstest/user-files/data/motorRegras.csv").queue

    class Brand(val name: String, val url: String, val body: String)

    private val global = new Brand(
        "Motor Regras",
        "/v1/transaction/fire",
        "/home/wagner.moura/stresstest/user-files/bodies/motorRegras.json")
    
    private def motorRegras(brand: Brand): ChainBuilder =
        exec(http(s"${brand.name}")
        .post("/v1/transaction/fire")
        .body(ElFileBody(brand.body))
        .check(status.in(200, 422)))

    val motor: ChainBuilder = feed(randomCsvMotor).exec(motorRegras(global))
}