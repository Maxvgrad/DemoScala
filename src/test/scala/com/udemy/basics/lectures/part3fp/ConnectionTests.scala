package com.udemy.basics.lectures.part3fp

import org.scalatest.FlatSpec

import scala.util.Try

class ConnectionTests extends FlatSpec {

  "Establish a connection" should "config is not set" in {

    val config = Map(
      "host" -> null,
    )
    assertThrows[IllegalArgumentException](ConnectionHelper(config))
  }

  "Establish a connection" should "config is good" in {

    val config = Map(
      "host" -> "127.0.0.1",
      "port" -> "80",
    )

    assertResult("Connected") {
      HeavyConnectionHelper(config).get
    }
  }

  "Render html" should "retrieve connection" in {

    val host = "127.0.0.1"
    val port = "80"

    val response = Try {HttpService.getConnection(host, port)}.flatMap(con => Try{ con.connect })

    if (response.isSuccess) response.get
  }

}
