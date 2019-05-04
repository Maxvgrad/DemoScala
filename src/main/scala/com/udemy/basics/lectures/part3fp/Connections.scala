package com.udemy.basics.lectures.part3fp

import scala.util.Random

object ConnectionHelper {

  def apply(config: Map[String, String]) = {

    val port = config.get("port")
    val host = config.get("host")

    if (port.isEmpty) throw new IllegalArgumentException("port")
    if (host.isEmpty) throw new IllegalArgumentException("host")

    val connectionOpt = Connection(host.get, port.get)

    if (connectionOpt.isEmpty) throw new IllegalArgumentException
    connectionOpt.get
  }
}

object HeavyConnectionHelper {

  def apply(config: Map[String, String]) = {
    val connection = config.get("port").flatMap(p => config.get("host").flatMap(h => Connection(h, p)))
    connection.map(c => c.connect)
  }
}

object ForComprehensionConnectionHelper {

  def apply(config: Map[String, String]) = {
    for {
      host <- config.get("host")
      port <- config.get("port")
      connection <- Connection(host, port)
    } yield connection.connect
  }
}

class Connection {
  val random = new Random(System.nanoTime())

  def connect =
    if (random.nextBoolean()) "<html>...</html>"
    else throw new RuntimeException
}

object Connection {

  val random = new Random(System.nanoTime())

  def apply(host: String, port: String): Option[Connection] =
    if (random.nextBoolean()) Some(new Connection)
    else None
}

object HttpService {
  val random = new Random(System.nanoTime())

  def getConnection(host: String, port: String) =
    if (random.nextBoolean()) new Connection
    else throw new RuntimeException()
}