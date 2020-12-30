package br.com.zoop.workload

import br.com.zoop.mfa.MultiFactor
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class MfaSimulation extends Simulation {
  val httpConf = http.baseUrl("https://0swb1u1mp4.execute-api.us-east-1.amazonaws.com")

  val scnMfa = scenario("MFA")
    .exec(MultiFactor.testeMfa)
    .pause(1, 3 seconds)

  setUp(
    scnMfa.inject(
//     atOnceUsers(1)

      rampUsersPerSec(1).to(25).during(5 minutes),
      constantUsersPerSec(25).during(2 minutes),
      rampUsersPerSec(25).to(60).during(5 minutes),
      constantUsersPerSec(60).during(2 minutes)
    )
  ).protocols(httpConf)
}

