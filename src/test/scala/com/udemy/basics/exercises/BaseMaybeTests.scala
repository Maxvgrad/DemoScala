package com.udemy.basics.exercises

import org.scalatest.FlatSpec

class BaseMaybeTests extends FlatSpec {

  "A Maybe[A].map " should "get Maybe[C]" in {
    val maybe = Maybe(5)
    val maybeStr = maybe.map((n: Int) => n.toString)
    assertResult("5")(maybeStr.element)
  }

  "A Maybe[A].filter" should "return Empty" in {
    val maybe = Maybe("tom Cruise")
    assertResult(AnEmpty)(maybe.filter((s: String) => s.startsWith("Tom")))
  }

  "A Maybe[A].filter" should "return this" in {
    val maybe = Maybe("Tom Cruise")
    assertResult(maybe)(maybe.filter((s: String) => s.startsWith("Tom")))
  }

}
