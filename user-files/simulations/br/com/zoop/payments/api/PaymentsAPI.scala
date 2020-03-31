package br.com.zoop.payments.api

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object PaymentsAPI {
    val porVendedor = exec(http("Payments Vendas por vendedor")
        .get("/v1/marketplaces/0162893710a6495e86542eeff192baa1/sellers/d72a6137bfe54d8cbaa68d6df8990845/transactions?status=succeeded")
        .header("Authorization", "Basic enBrX3Byb2RfTzZXbzdrUnpZWDExVTBLUUN5NVlsOWh4Og==")
        .check(status.is(200)))

    val porIdParceiro = exec(http("Payments Id Parceiro")
        .get("/v1/marketplaces/0162893710a6495e86542eeff192baa1/transactions?reference_id=400037659938")
        .header("Authorization", "Basic enBrX3Byb2RfTzZXbzdrUnpZWDExVTBLUUN5NVlsOWh4Og==")
        .check(status.is(200)))

    val porMarketPlaceID = exec(http("Payments por Marketplace ID")
        .get("/v1/marketplaces/0162893710a6495e86542eeff192baa1/transactions")
        .header("Authorization", "Basic enBrX3Byb2RfTzZXbzdrUnpZWDExVTBLUUN5NVlsOWh4Og==")
        .check(status.is(200)))

    val cargaTabelaV1 = exec(http("Carga Tabela V1")
        .post("https://api.zoop.ws/v1/card-present/terminals/2945be08b7f043bf8d3adaede7ee8dca/logon")
        .header("Authorization", "Basic enBrX3Byb2RfTzZXbzdrUnpZWDExVTBLUUN5NVlsOWh4Og==")
        .header("Content-type", "application/json")
        .body(StringBody("{\n\t\"customer\": \"d72a6137bfe54d8cbaa68d6df8990845\",\n\t\"gateway_authorizer\": \"globalpayments\"\n}")).asJson
        .check(status.is(200)))
}