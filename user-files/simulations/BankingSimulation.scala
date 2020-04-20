package br.com.zoop.workload

import br.com.zoop.banking.api.Banking
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BankingSimulation extends Simulation {
  val httpConf = http.baseUrl("https://banking-card-manager-internal.staging.zoop.tech")

  val scnTypi = scenario("Banking")
    .exec(Banking.bankingTest)
    .pause(1, 3 seconds)

  setUp(
    scnTypi.inject(
      //atOnceUsers(1)
      rampUsersPerSec(1).to(50).during(10 minutes),
      constantUsersPerSec(50).during(5 minutes),
      rampUsersPerSec(50).to(100).during(10 minutes),
      constantUsersPerSec(100).during(5 minutes)
    )
  ).protocols(httpConf)
}
