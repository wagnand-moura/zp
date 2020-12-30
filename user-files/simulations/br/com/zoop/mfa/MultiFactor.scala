package br.com.zoop.mfa

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object MultiFactor {

  private val csvMfa = csv("/home/wagner.moura/stresstest/user-files/data/mfa.csv").random

  class Brand(val name: String, val url: String, val body: String)

  private val mfa = new Brand(
    "MFA",
    "/default/v1/api/company/login",
    "/home/wagner.moura/stresstest/user-files/bodies/mfa.json")

  private def getToken(brand: Brand): HttpRequestBuilder =
    http(s"MFA Create ${brand.name}")
      .post(brand.url)
      .headers(Map("Content-Type" -> "application/json",
      "x-api-key" -> "TdTu96LLQJ5etSvgudqm2fVhmQvfMhA8sktXL3z9"))
      .body(ElFileBody(brand.body))
      .check(status.is(200))
      .check(jsonPath("$..X-Auth-Token").saveAs("xauthtoken"))

  private def criarSessao(brand: Brand): ChainBuilder =
    exec(getToken(brand))
      .exitHereIfFailed
      .exec(http(s"Create Session ${brand.name}")
        .post("/default/v1/api/user/cellphone/session")
        .headers(
          Map(
            "Content-Type" -> "application/json",
            "x-api-key" -> "TdTu96LLQJ5etSvgudqm2fVhmQvfMhA8sktXL3z9",
            "X-Auth-Token" -> "${xauthtoken}")
        )
        .body(ElFileBody("mfaSessionBody.json"))
        .check(status.is(200)))

  val testeMfa: ChainBuilder = feed(csvMfa).exec(criarSessao(mfa))
}
