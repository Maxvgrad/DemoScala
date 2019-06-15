package com.udemy.advanced.lecture.part2afp

import org.scalatest.FlatSpec

class MonadTest extends FlatSpec {

  behavior of "monads"

  it should "left identity" in {
    def power(n: Int): Option[Int] = Some(n * n)
    val x = 2

    //unit(x).flatMap(f) == f(x)
    assertResult(power(2))(Option(2).flatMap(power(_)))
  }

  it should "right identity" in {
    val monad: Option[Int] = Some(4)

    //monad.flatMap(unit) == monad
    assertResult(monad)(monad.flatMap(Option(_)))
  }

  it should "associativity" in {
    def f(n: Int): Option[Int] = Some(n * n)
    def g(n: Int): Option[Int] = Some(n * n)
    val monad = Option(500)

    //monad.flatMap(f).flatMap(g) == monad.flatMap(x => f(x).flatMap(g))
    assertResult(monad.flatMap(x => f(x).flatMap(g)))(monad.flatMap(f).flatMap(g))
  }
}
