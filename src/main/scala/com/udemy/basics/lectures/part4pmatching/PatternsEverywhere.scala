package com.udemy.basics.lectures.part4pmatching

object PatternsEverywhere extends App {

  // big idea #1

  try {

  } catch {
    case e: RuntimeException =>
    case npe: NullPointerException =>
    case _ =>
  }

  val list = List(1,2,3,4)

  val even = for {
    x <- list if x % 2 == 0
  } yield 10 * x

}
