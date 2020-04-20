package br.com.zoop.banking.api

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.structure.ChainBuilder

object Banking {
  private val csvBanking = csv("/home/wagner.moura/stresstest/user-files/data/banking.csv").queue

  class Brand(val name: String, val url: String, val body: String)

  private val global = new Brand(
    "Banking",
    "/transaction",
    "/home/wagner.moura/stresstest/user-files/bodies/banking.json")

  private def banking(brand: Brand): ChainBuilder =
    exec(http(s"${brand.name}")
      .post("/transaction")
      .body(ElFileBody(brand.body))
      .check(status.in(200))
      .check(jsonPath("$[?(@.result=='00')]").exists))

  val bankingTest: ChainBuilder = feed(csvBanking).exec(banking(global))
}
