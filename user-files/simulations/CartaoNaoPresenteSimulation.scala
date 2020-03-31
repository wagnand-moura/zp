//package br.com.zoop.workload

import br.com.zoop.payments.api._
import io.gatling.core.Predef._
import scala.concurrent.duration._

class CartaoNaoPresenteSimulation extends Simulation {

  val scnOneShot = scenario("One Shot")
    .exec(CartaoNaoPresente.oneShot)
    .pause(1, 3 seconds)

  setUp(
    scnOneShot.inject(
      rampUsersPerSec(1).to(30).during(5 minutes),
      constantUsersPerSec(30).during(5 minutes)
    )
  )
}
