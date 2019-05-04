package com.udemy.basics.lectures.part4pmatching

import org.scalatest.FlatSpec

class ExpressionsTests extends FlatSpec {

  it should "return correct equation" in {
    val expr = new Expressions

    assertResult("3") {
      expr.show(Number(3))
    }

    assertResult("3 + 5") {
      expr.show(Sum(Number(3), Number(5)))
    }

    assertResult("3 * 5") {
      expr.show(Prod(Number(3), Number(5)))
    }

    assertResult("3 * 5 + 2") {
      expr.show(Sum(Prod(Number(3), Number(5)), Number(2)))
    }

    assertResult("(3 + 5) * 2") {
      expr.show(Prod(Sum(Number(3), Number(5)), Number(2)))
    }

  }

}
