package br.com.zoop.rules.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder

object Rules {
    private val randomCsvMotor = csv("/home/wagner.moura/stresstest/user-files/data/motorRegras.csv").random

    class Brand(val name: String, val url: String, val body: String)

    val headers = Map(
        "Content-Type" -> "application/json",
        "Accept" -> "application/vnd.rulesengine.app-v1.0+json"
    )

    private val global = new Brand(
        "Motor Regras",
        "/transaction/fire",
        "/home/wagner.moura/stresstest/user-files/bodies/motorRegras.json")

    private val stateful = new Brand(
        "Motor Regras",
        "/transaction/stateful/fire",
        "motorRegras.json")
    
    private def motorRegras(brand: Brand): ChainBuilder =
        exec(http(s"${brand.name}")
        .post(brand.url)
        .body(ElFileBody(brand.body))
        .headers(headers)
        .check(status.in(200, 422)))

    val motor: ChainBuilder = feed(randomCsvMotor).exec(motorRegras(global))

    val motorStateful: ChainBuilder = feed(randomCsvMotor).exec(motorRegras(stateful))
}