import br.com.zoop.payments.api._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PaymentsSimulation extends Simulation {

  val httpConf = http.baseUrl("https://api.zoop.ws")

  val scnPreAutorizacaoVoid = scenario("Payments - Por Vendedor")
    .exec(PaymentsAPI.porVendedor).pause(1 second)
    .exec(PaymentsAPI.porIdParceiro).pause(1 second)
    .exec(PaymentsAPI.porMarketPlaceID).pause(1 second)
    .exec(PaymentsAPI.cargaTabelaV1).pause(1 second)

  val scnCartaoPresente = scenario("Cartao Presente")
    .exec(CartaoPresente.comConfirmacaoCielo).pause(1 second)

  setUp(
    // scnPreAutorizacaoVoid.inject(
    //   atOnceUsers(1)
    //   //rampUsersPerSec(1).to(30).during(5 minutes),
    //   //constantUsersPerSec(30).during(5 minutes)

      scnCartaoPresente.inject(
        rampUsersPerSec(1).to(1000).during(60 minutes)
      
    )
  ).protocols(httpConf)
}