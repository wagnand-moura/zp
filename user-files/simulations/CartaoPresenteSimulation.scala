//package br.com.zoop.workload

import br.com.zoop.payments.api._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class CartaoPresenteSimulation extends Simulation {

  val httpConf = http.baseUrl("https://api.zoop.ws")

  val scnPreAutorizacaoVoid = scenario("CP - Pré-autorização com void")
    .exec(CartaoPresente.comReversaCielo)
    .pause(1, 3 seconds)

  setUp(
    scnPreAutorizacaoVoid.inject(
      atOnceUsers(1)
      //rampUsersPerSec(1).to(30).during(5 minutes),
      //constantUsersPerSec(30).during(5 minutes)
    )
  ).protocols(httpConf)
}
