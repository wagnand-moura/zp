import br.com.zoop.gsp._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class GspSimulation extends Simulation {

  val httpConf = http.baseUrl("https://api.staging.pagzoop.com")

  val scn = scenario("Teste de Carga GSP Transfer")
    .exec(Autenticacao.requestNovo).pause(1, 3 seconds)

  setUp(
    scnTransacional.inject(
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
  ).protocols(httpConf)
}