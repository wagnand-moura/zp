import br.com.zoop.payments.api._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StressTestSimulation extends Simulation {

  val httpConf = http.baseUrl("https://api-stresstest.zoop.ws")

  val scnNovo = scenario("Teste de Carga")
    .exec(Autenticacao.requestNovo).pause(1, 3 seconds)

  val scnProd = scenario("Teste de Carga")
    .exec(Autenticacao.requestAtualProd).pause(1, 3 seconds)

  setUp(
    scnNovo.inject(
      //atOnceUsers(1)
      rampUsersPerSec(1).to(250).during(10 minutes),
      constantUsersPerSec(250).during(5 minutes),
      rampUsersPerSec(250).to(500).during(10 minutes),
      constantUsersPerSec(500).during(5 minutes),

      rampUsersPerSec(500).to(750).during(10 minutes),
      constantUsersPerSec(750).during(5 minutes),
      rampUsersPerSec(750).to(1000).during(10 minutes),
      constantUsersPerSec(1000).during(5 minutes)
    )
//    scnProd.inject(
//      rampUsersPerSec(1).to(50).during(10 minutes),
//      constantUsersPerSec(50).during(5 minutes),
//      rampUsersPerSec(50).to(100).during(10 minutes),
//      constantUsersPerSec(100).during(5 minutes)
//    )
  ).protocols(httpConf)
}